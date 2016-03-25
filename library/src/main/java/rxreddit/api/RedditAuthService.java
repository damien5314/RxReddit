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
import rx.functions.Action0;
import rxreddit.Util;
import rxreddit.model.AccessToken;
import rxreddit.model.ApplicationAccessToken;
import rxreddit.model.UserAccessToken;

final class RedditAuthService implements IRedditAuthService {
  private static final String RESPONSE_TYPE = "code";
  private static final String DURATION = "permanent";
  private static final String STATE = Util.getRandomString();
  private static final String SCOPE = Util.join(",",
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

  public RedditAuthService(
      String clientId, String redirectUri, String deviceId, String userAgent,
      AccessTokenManager atm) {
    mRedirectUri = redirectUri;
    mDeviceId = deviceId;
    mUserAgent = userAgent;
    mHttpAuthHeader = Credentials.basic(clientId, "");
    mAuthorizationUrl =
        String.format("https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
                "&response_type=%s&duration=%s&state=%s&redirect_uri=%s&scope=%s",
            clientId, RESPONSE_TYPE, DURATION, STATE, redirectUri, SCOPE);
    mAuthService = buildApi();
    mAccessTokenManager = atm;
  }

  @Override
  public AccessToken getAccessToken() {
    AccessToken token = mAccessTokenManager.getUserAccessToken();
    if (isValid(token)) return token;
    token = mAccessTokenManager.getApplicationAccessToken();
    if (isValid(token)) return token;
    return null;
  }

  @Override
  public Observable<AccessToken> refreshAccessToken() {
    return refreshUserAccessToken()
        .map(token -> (AccessToken) token)
        .onErrorResumeNext(getApplicationAccessToken());
  }

  @Override
  public Observable<UserAccessToken> refreshUserAccessToken() {
    return Observable.defer(() -> {
      UserAccessToken token = mAccessTokenManager.getUserAccessToken();
      if (token == null) {
        return Observable.error(new RuntimeException("No user access token available"));
      }
      if (token.secondsUntilExpiration() > EXPIRATION_THRESHOLD) {
        return Observable.just(token);
      }
      String refreshToken = token.getRefreshToken();
      if (refreshToken == null) {
        mClearIdentity.call();
        return Observable.error(new RuntimeException("No refresh token available"));
      }
      String grantType = "refresh_token";
      return mAuthService.refreshUserAuthToken(grantType, refreshToken)
          .map(Response::body)
          .doOnNext(mAccessTokenManager.saveUserAccessToken())
          // FIXME We should actually be discarding this in onNext when response code != 2xx
          .doOnError(e -> mClearIdentity.call());
    });
  }

  private Observable<ApplicationAccessToken> getApplicationAccessToken() {
    return Observable.defer(() -> {
      ApplicationAccessToken token = mAccessTokenManager.getApplicationAccessToken();
      if (token != null && token.secondsUntilExpiration() > EXPIRATION_THRESHOLD) {
        return Observable.just(token);
      } else {
        String grantType = "https://oauth.reddit.com/grants/installed_client";
        return mAuthService.getApplicationAuthToken(grantType, mDeviceId)
            .map(Response::body)
            .doOnNext(mAccessTokenManager.saveApplicationAccessToken());
      }
    });
  }

  private Action0 mClearIdentity = () -> {
    mAccessTokenManager.clearSavedUserAccessToken();
    // TODO Wipe identity during clear
//    mIdentityManager.clearSavedUserIdentity();
  };

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
    if (!STATE.equals("state")) {
      return Observable.error(new RuntimeException("State does not match, abort authorization"));
    }
    String grantType = "authorization_code";
    return mAuthService.getUserAuthToken(grantType, authCode, mRedirectUri)
        .map(Response::body)
        .doOnNext(mAccessTokenManager.saveUserAccessToken());
  }

  @Override
  public boolean isUserAuthorized() {
    return mAccessTokenManager.getUserAccessToken() != null;
  }

  @Override
  public Observable<Void> revokeAuthentication() {
    AccessToken token = mAccessTokenManager.getUserAccessToken();
    if (token != null) return revokeAuthToken(token);
    else return Observable.just(null);
  }

  private Observable<Void> revokeAuthToken(AccessToken token) {
    return Observable.merge(
        mAuthService.revokeUserAuthToken(token.getToken(), "access_token"),
        mAuthService.revokeUserAuthToken(token.getRefreshToken(), "refresh_token"));
  }

  private RedditAuthAPI buildApi() {
    OkHttpClient client = new OkHttpClient.Builder()
        .addNetworkInterceptor(new UserAgentInterceptor(mUserAgent))
        .addNetworkInterceptor(getHttpAuthInterceptor())
        .build();
    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create();
    Retrofit restAdapter = new Retrofit.Builder()
        .client(client)
        .baseUrl("https://www.reddit.com")
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
