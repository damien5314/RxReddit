package rxreddit.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rxreddit.model.AccessToken;
import rxreddit.model.ApplicationAccessToken;
import rxreddit.model.UserAccessToken;
import rxreddit.util.RxRedditUtil;


final class RedditAuthService implements IRedditAuthService {

    static final String BASE_URL = "https://www.reddit.com";
    static final String RESPONSE_TYPE = "code";
    static final String DURATION = "permanent";
    static final String STATE = RxRedditUtil.getRandomString();
    static final String SCOPE = StringUtils.join(",",
            new String[] {
                    "identity", "mysubreddits", "privatemessages", "read", "report", "save",
                    "submit", "vote", "history", "account", "subscribe" });
    // Seconds within expiration we should try to retrieve a new auth token
    private static final int EXPIRATION_THRESHOLD = 60;

    private final String redirectUri;
    private final String deviceId;
    private final String userAgent;
    private final String httpAuthHeader;
    private final String authorizationUrl;
    private RedditAuthAPI authService;
    private AccessTokenManager accessTokenManager;

    private UserAccessToken mUserAccessToken;
    private ApplicationAccessToken mApplicationAccessToken;

    public RedditAuthService(
            String baseUrl, String clientId, String redirectUri, String deviceId, String userAgent,
            AccessTokenManager atm, boolean loggingEnabled) {
        this.redirectUri = redirectUri;
        this.deviceId = deviceId;
        this.userAgent = userAgent;
        httpAuthHeader = Credentials.basic(clientId, "");
        authorizationUrl =
                String.format("https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
                                "&response_type=%s&duration=%s&state=%s&redirect_uri=%s&scope=%s",
                        clientId, RESPONSE_TYPE, DURATION, STATE, redirectUri, SCOPE);
        authService = buildApi(baseUrl, loggingEnabled);
        accessTokenManager = atm;
    }

    @Override
    public boolean isUserAuthorized() {
        return getUserAccessToken() != null;
    }

    @Override
    public AccessToken getAccessToken() {
        AccessToken token = getUserAccessToken();
        if (isValid(token)) return token;
        return getApplicationAccessToken();
    }

    private UserAccessToken getUserAccessToken() {
        if (mUserAccessToken == null) {
            mUserAccessToken = accessTokenManager.getUserAccessToken();
        }
        return mUserAccessToken;
    }

    private ApplicationAccessToken getApplicationAccessToken() {
        if (mApplicationAccessToken == null) {
            mApplicationAccessToken = accessTokenManager.getApplicationAccessToken();
        }
        return mApplicationAccessToken;
    }

    private Observable<ApplicationAccessToken> refreshApplicationAccessToken() {
        return Observable.defer(() -> {
            ApplicationAccessToken token = getApplicationAccessToken();
            if (token != null && token.secondsUntilExpiration() > EXPIRATION_THRESHOLD) {
                return Observable.just(token);
            } else {
                String grantType = "https://oauth.reddit.com/grants/installed_client";
                return authService.getApplicationAuthToken(grantType, deviceId)
                        .flatMap(RxRedditUtil::responseToBody)
                        .doOnNext(this::saveApplicationAccessToken);
            }
        });
    }

    @Override
    public Observable<AccessToken> refreshAccessToken() {
        return refreshUserAccessToken()
                .map(token -> (AccessToken) token)
                .onErrorResumeNext(refreshApplicationAccessToken());
    }

    @Override
    public Observable<UserAccessToken> refreshUserAccessToken() {
        return Observable.defer(() -> {
            UserAccessToken token = getUserAccessToken();
            if (token == null) {
                return Observable.error(
                        new IllegalStateException("No user access token available"));
            }
            if (token.secondsUntilExpiration() > EXPIRATION_THRESHOLD) {
                return Observable.just(token);
            }
            String refreshToken = token.getRefreshToken();
            if (refreshToken == null) {
                clearUserAccessToken();
                return Observable.error(
                        new IllegalStateException("No refresh token available"));
            }
            String grantType = "refresh_token";
            return authService.refreshUserAuthToken(grantType, refreshToken)
                    .flatMap(RxRedditUtil::responseToBody)
                    .doOnNext(this::saveUserAccessToken)
                    .doOnError(error -> {
                        if (error instanceof HttpException && ((HttpException) error).code() == 403) {
                            // 403 means our refresh token is no longer good, just discard it
                            clearUserAccessToken();
                        }
                    });
        });
    }

    private void saveUserAccessToken(UserAccessToken token) {
        if (token.getRefreshToken() == null) {
            UserAccessToken storedToken = accessTokenManager.getUserAccessToken();
            if (storedToken != null) {
                // Refresh token doesn't come back in a refresh, so we have to use the one stored
                token.setRefreshToken(storedToken.getRefreshToken());
            }
        }
        mUserAccessToken = token;
        accessTokenManager.saveUserAccessToken(token);
    }

    private void saveApplicationAccessToken(ApplicationAccessToken token) {
        mApplicationAccessToken = token;
        accessTokenManager.saveApplicationAccessToken(token);
    }

    private void clearUserAccessToken() {
        mUserAccessToken = null;
        accessTokenManager.clearSavedUserAccessToken();
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    @Override
    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    @Override
    public Observable<UserAccessToken> onAuthCodeReceived(String authCode, String state) {
        if (!STATE.equals(state)) {
            return Observable.error(
                    new IllegalStateException("State does not match, abort authentication"));
        }
        String grantType = "authorization_code";
        return authService.getUserAuthToken(grantType, authCode, redirectUri)
                .flatMap(RxRedditUtil::responseToBody)
                .doOnNext(this::saveUserAccessToken);
    }

    @Override
    public Completable revokeUserAuthentication() {
        AccessToken token = getUserAccessToken();
        mUserAccessToken = null;
        accessTokenManager.clearSavedUserAccessToken();
        return revokeAuthToken(token);
    }

    private Completable revokeAuthToken(AccessToken token) {
        if (token == null) return Completable.error(new NullPointerException("token == null"));
        return Observable.merge(
                authService.revokeUserAuthToken(token.getToken(), "access_token")
                        .flatMap(RxRedditUtil::checkResponse),
                authService.revokeUserAuthToken(token.getRefreshToken(), "refresh_token")
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    private RedditAuthAPI buildApi(String baseUrl, boolean loggingEnabled) {
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder()
                .addNetworkInterceptor(new UserAgentInterceptor(userAgent))
                .addNetworkInterceptor(getHttpAuthInterceptor());

        if (loggingEnabled) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            okhttp.addInterceptor(loggingInterceptor);
        }

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit restAdapter = new Retrofit.Builder()
                .client(okhttp.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return restAdapter.create(RedditAuthAPI.class);
    }

    private boolean isValid(AccessToken token) {
        return token != null && token.secondsUntilExpiration() > EXPIRATION_THRESHOLD;
    }

    private Interceptor getHttpAuthInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            Request newRequest = originalRequest.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", httpAuthHeader)
                    .build();
            return chain.proceed(newRequest);
        };
    }
}
