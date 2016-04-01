package rxreddit.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Action1;
import rxreddit.RxRedditUtil;
import rxreddit.model.AccessToken;
import rxreddit.model.ApplicationAccessToken;
import rxreddit.model.UserAccessToken;

import static rxreddit.api.RedditService.responseToBody;

final class RedditAuthService implements IRedditAuthService {
  static final String BASE_URL = "https://www.reddit.com";
  static final String RESPONSE_TYPE = "code";
  static final String DURATION = "permanent";
  static final String STATE = RxRedditUtil.getRandomString();
  static final String SCOPE = RxRedditUtil.join(",",
      new String[] {
          "identity", "mysubreddits", "privatemessages", "read", "report", "save",
          "submit", "vote", "history", "account", "subscribe" });
  // Seconds within expiration we should try to retrieve a new auth token
  private static final int EXPIRATION_THRESHOLD = 60;

  private final String mRedirectUri;
  private final String mDeviceId;
  private final String mUserAgent;
  private final String mHttpAuthHeader;
  private final String mAuthorizationUrl;
  private RedditAuthAPI mAuthService;
  private AccessTokenManager mAccessTokenManager;

  private UserAccessToken mUserAccessToken;
  private ApplicationAccessToken mApplicationAccessToken;

  public RedditAuthService(
      String baseUrl, String clientId, String redirectUri, String deviceId, String userAgent,
      AccessTokenManager atm) {
    mRedirectUri = redirectUri;
    mDeviceId = deviceId;
    mUserAgent = userAgent;
    mHttpAuthHeader = Credentials.basic(clientId, "");
    mAuthorizationUrl =
        String.format("https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
                "&response_type=%s&duration=%s&state=%s&redirect_uri=%s&scope=%s",
            clientId, RESPONSE_TYPE, DURATION, STATE, redirectUri, SCOPE);
    mAuthService = buildApi(baseUrl);
    mAccessTokenManager = atm;
  }

  @Override
  public boolean isUserAuthorized() {
    return getUserAccessToken() != null;
  }

  @Override
  public AccessToken getAccessToken() {
    AccessToken token = getUserAccessToken();
    if (isValid(token)) return token;
    token = getApplicationAccessToken();
    if (isValid(token)) return token;
    return null;
  }

  private UserAccessToken getUserAccessToken() {
    if (mUserAccessToken == null) {
      mUserAccessToken = mAccessTokenManager.getUserAccessToken();
    }
    return mUserAccessToken;
  }

  private ApplicationAccessToken getApplicationAccessToken() {
    if (mApplicationAccessToken == null) {
      mApplicationAccessToken = mAccessTokenManager.getApplicationAccessToken();
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
        return mAuthService.getApplicationAuthToken(grantType, mDeviceId)
            .flatMap(responseToBody())
            .doOnNext(saveApplicationAccessToken());
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
        clearUserIdentity();
        return Observable.error(
            new IllegalStateException("No refresh token available"));
      }
      String grantType = "refresh_token";
      return mAuthService.refreshUserAuthToken(grantType, refreshToken)
          .flatMap(responseToBody())
          .doOnNext(saveUserAccessToken())
          .doOnError(error -> clearUserIdentity());
    });
  }

  private Action1<UserAccessToken> saveUserAccessToken() {
    return token -> {
      if (token.getRefreshToken() == null) {
        UserAccessToken storedToken = mAccessTokenManager.getUserAccessToken();
        if (storedToken != null) {
          token.setRefreshToken(storedToken.getRefreshToken());
        }
      }
      mUserAccessToken = token;
      mAccessTokenManager.saveUserAccessToken(token);
    };
  }

  private Action1<ApplicationAccessToken> saveApplicationAccessToken() {
    return token -> {
      mApplicationAccessToken = token;
      mAccessTokenManager.saveApplicationAccessToken(token);
    };
  }

  private void clearUserIdentity() {
    mUserAccessToken = null;
    mAccessTokenManager.clearSavedUserAccessToken();
  }

  @Override
  public String getRedirectUri() {
    return mRedirectUri;
  }

  @Override
  public String getAuthorizationUrl() {
    return mAuthorizationUrl;
  }

  @Override
  public Observable<UserAccessToken> onAuthCodeReceived(String authCode, String state) {
    if (!STATE.equals(state)) {
      return Observable.error(
          new IllegalStateException("State does not match, abort authentication"));
    }
    String grantType = "authorization_code";
    return mAuthService.getUserAuthToken(grantType, authCode, mRedirectUri)
        .map(Response::body)
        .doOnNext(saveUserAccessToken());
  }

  @Override
  public Observable<Void> revokeUserAuthentication() {
    AccessToken token = getUserAccessToken();
    mUserAccessToken = null;
    mAccessTokenManager.clearSavedUserAccessToken();
    return revokeAuthToken(token);
  }

  private Observable<Void> revokeAuthToken(AccessToken token) {
    if (token == null) return Observable.error(new IllegalStateException("token == null"));
    return Observable.merge(
        mAuthService.revokeUserAuthToken(token.getToken(), "access_token")
            .flatMap(responseToBody()),
        mAuthService.revokeUserAuthToken(token.getRefreshToken(), "refresh_token")
            .flatMap(responseToBody())
    );
  }

  private RedditAuthAPI buildApi(String baseUrl) {
    OkHttpClient client = new OkHttpClient.Builder()
        .addNetworkInterceptor(new UserAgentInterceptor(mUserAgent))
        .addNetworkInterceptor(getHttpAuthInterceptor())
        .build();
    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
    Retrofit restAdapter = new Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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
          .addHeader("Authorization", mHttpAuthHeader)
          .build();
      return chain.proceed(newRequest);
    };
  }
}
