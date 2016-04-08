package rxreddit.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;
import rxreddit.RxRedditUtil;
import rxreddit.model.AbsComment;
import rxreddit.model.AccessToken;
import rxreddit.model.Comment;
import rxreddit.model.Friend;
import rxreddit.model.FriendInfo;
import rxreddit.model.Listing;
import rxreddit.model.ListingResponse;
import rxreddit.model.MoreChildrenResponse;
import rxreddit.model.Subreddit;
import rxreddit.model.UserAccessToken;
import rxreddit.model.UserIdentity;
import rxreddit.model.UserIdentityListing;
import rxreddit.model.UserSettings;

public class RedditService implements IRedditService {
  public static final String BASE_URL = "https://oauth.reddit.com";

  private RedditAPI mAPI;
  private IRedditAuthService mRedditAuthService;

  protected RedditService(
      String baseUrl, String baseAuthUrl, String redditAppId, String redirectUri,
      String deviceId, String userAgent, AccessTokenManager atm) {
    mRedditAuthService =
        new RedditAuthService(baseAuthUrl, redditAppId, redirectUri, deviceId, userAgent, atm);
    mAPI = buildApi(baseUrl, userAgent);
  }

  @Override
  public String getRedirectUri() {
    return mRedditAuthService.getRedirectUri();
  }

  @Override
  public String getAuthorizationUrl() {
    return mRedditAuthService.getAuthorizationUrl();
  }

  @Override
  public boolean isUserAuthorized() {
    return mRedditAuthService.isUserAuthorized();
  }

  @Override
  public Observable<UserAccessToken> processAuthenticationCallback(String callbackUrl) {
    Map<String, String> params;
    try {
      params = RxRedditUtil.getQueryParametersFromUrl(callbackUrl);
    } catch (Exception e) {
      return Observable.error(new IllegalArgumentException("invalid callback url: " + callbackUrl));
    }
    if (params.containsKey("error")) {
      return Observable.error(
          new IllegalStateException("Error during user authentication: " + params.get("error")));
    }
    String authCode = params.get("code");
    if (authCode == null)
      return Observable.error(new IllegalArgumentException("invalid callback url: " + callbackUrl));
    String state = params.get("state");
    return mRedditAuthService.onAuthCodeReceived(authCode, state);
  }

  @Override
  public Observable<UserIdentity> getUserIdentity() {
    return requireUserAccessToken().flatMap(token ->
        mAPI.getUserIdentity()
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<UserSettings> getUserSettings() {
    return requireUserAccessToken().flatMap(token ->
        mAPI.getUserSettings()
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> updateUserSettings(Map<String, String> settings) {
    String json = new Gson().toJson(settings);
    RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
    return requireUserAccessToken().flatMap(token ->
        mAPI.updateUserSettings(body)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<ListingResponse> loadLinks(
      String subreddit, String sort, String timespan, String before, String after) {
    return requireAccessToken().flatMap(token ->
        mAPI.getLinks(sort != null ? sort : "hot", subreddit, timespan, before, after)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<List<ListingResponse>> loadLinkComments(
      String subreddit, String article, String sort, String commentId) {
    if (subreddit == null)
      return Observable.error(new IllegalArgumentException("subreddit == null"));
    if (article == null)
      return Observable.error(new IllegalArgumentException("article == null"));
    return requireAccessToken().flatMap(token ->
        mAPI.getComments(subreddit, article, sort, commentId)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<MoreChildrenResponse> loadMoreChildren(
      String linkId, List<String> childrenIds, String sort) {
    if (linkId == null)
      return Observable.error(new IllegalArgumentException("linkId == null"));
    if (childrenIds == null || childrenIds.size() == 0)
      return Observable.error(new IllegalArgumentException("no comment IDs provided"));
    return requireAccessToken().flatMap(token ->
        mAPI.getMoreChildren("t3_" + linkId, RxRedditUtil.join(",", childrenIds), sort)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<UserIdentity> getUserInfo(String username) {
    return requireAccessToken().flatMap(token ->
        mAPI.getUserInfo(username)
            .flatMap(responseToBody())
            .map(UserIdentityListing::getUser));
  }

  @Override
  public Observable<FriendInfo> getFriendInfo(String username) {
    return requireUserAccessToken().flatMap(token ->
        mAPI.getFriendInfo(username)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<List<Listing>> getUserTrophies(String username) {
    // TODO: This should return a List of Trophies instead of Listings
    if (username == null)
      return Observable.error(new IllegalArgumentException("username == null"));
    return requireAccessToken().flatMap(token ->
        mAPI.getUserTrophies(username)
            .flatMap(responseToBody())
            .map(trophyResponse -> trophyResponse.getData().getTrophies()));
  }

  @Override
  public Observable<ListingResponse> loadUserProfile(
      String show, String username, String sort, String timespan, String before, String after) {
    if (show == null)
      return Observable.error(new IllegalArgumentException("show == null"));
    if (username == null)
      return Observable.error(new IllegalArgumentException("username == null"));
    return requireAccessToken().flatMap(token ->
        mAPI.getUserProfile(show, username, sort, timespan, before, after)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> addFriend(String username) {
    if (username == null)
      return Observable.error(new IllegalArgumentException("username == null"));
    RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{}");
    return requireUserAccessToken().flatMap(token ->
        mAPI.addFriend(username, body)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> deleteFriend(String username) {
    return requireUserAccessToken().flatMap(token ->
        mAPI.deleteFriend(username)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> saveFriendNote(String username, String note) {
    if (RxRedditUtil.isEmpty(note))
      return Observable.error(
          new IllegalArgumentException("user note should be non-empty"));
    String json = new Gson().toJson(new Friend(note));
    return requireUserAccessToken().flatMap(token ->
        mAPI.addFriend(username, RequestBody.create(MediaType.parse("application/json"), json))
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Subreddit> getSubredditInfo(String subreddit) {
    if (subreddit == null)
      return Observable.error(new IllegalArgumentException("subreddit == null"));
    return requireAccessToken().flatMap(token ->
        mAPI.getSubredditInfo(subreddit)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> vote(String fullname, int direction) {
    if (fullname == null)
      return Observable.error(new IllegalArgumentException("fullname == null"));
    return requireUserAccessToken().flatMap(token ->
        mAPI.vote(fullname, direction)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> save(String fullname, String category, boolean toSave) {
    if (fullname == null)
      return Observable.error(new IllegalArgumentException("fullname == null"));
    if (toSave) { // Save
      return requireUserAccessToken().flatMap(token ->
          mAPI.save(fullname, category)
              .flatMap(responseToBody()));
    } else { // Unsave
      return requireUserAccessToken().flatMap(token ->
          mAPI.unsave(fullname)
              .flatMap(responseToBody()));
    }
  }

  @Override
  public Observable<Void> hide(String fullname, boolean toHide) {
    if (fullname == null)
      return Observable.error(new IllegalArgumentException("fullname == null"));
    if (toHide) { // Hide
      return requireUserAccessToken().flatMap(token ->
          mAPI.hide(fullname)
              .flatMap(responseToBody()));
    } else { // Unhide
      return requireUserAccessToken().flatMap(token ->
          mAPI.unhide(fullname)
              .flatMap(responseToBody()));
    }
  }

  @Override
  public Observable<Void> report(String id, String reason) {
    return Observable.error(new UnsupportedOperationException());
  }

  @Override
  public Observable<Comment> addComment(String parentId, String text) {
    if (parentId == null)
      return Observable.error(new IllegalArgumentException("parentId == null"));
    if (text == null)
      return Observable.error(new IllegalArgumentException("text == null"));
    return requireUserAccessToken().flatMap(token ->
        mAPI.addComment(parentId, text)
            .flatMap(responseToBody())
            .map(response -> {
              List<String> errors = response.getErrors();
              if (errors.size() > 0) {
                throw new IllegalStateException("an error occurred: " + response.getErrors().get(0));
              }
              return response.getComment();
            }));
  }

  @Override
  public Observable<ListingResponse> getInbox(String show, String before, String after) {
    if (show == null)
      return Observable.error(new IllegalArgumentException("show == null"));
    return requireUserAccessToken().flatMap(token ->
        mAPI.getInbox(show, before, after)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> markAllMessagesRead() {
    return requireUserAccessToken().flatMap(token ->
        mAPI.markAllMessagesRead()
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> markMessagesRead(String commaSeparatedFullnames) {
    // TODO: This should take a List of message fullnames and construct the parameter
    return requireUserAccessToken().flatMap(token ->
        mAPI.markMessagesRead(commaSeparatedFullnames)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> markMessagesUnread(String commaSeparatedFullnames) {
    // TODO: This should take a List of message fullnames and construct the parameter
    return requireUserAccessToken().flatMap(token ->
        mAPI.markMessagesUnread(commaSeparatedFullnames)
            .flatMap(responseToBody()));
  }

  @Override
  public Observable<Void> revokeAuthentication() {
    return mRedditAuthService.revokeUserAuthentication();
  }

  protected Observable<AccessToken> requireAccessToken() {
    return mRedditAuthService.refreshAccessToken();
  }

  protected Observable<UserAccessToken> requireUserAccessToken() {
    return mRedditAuthService.refreshUserAccessToken();
  }

  private RedditAPI buildApi(String baseUrl, String userAgent) {
    Retrofit restAdapter = new Retrofit.Builder()
        .client(getOkHttpClient(userAgent))
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return restAdapter.create(RedditAPI.class);
  }

  protected OkHttpClient getOkHttpClient(String userAgent) {
//    final int cacheSize = 10 * 1024 * 1024; // 10 MiB
//    File cache = new File(
//        HoldTheNarwhal.getContext().getCacheDir().getAbsolutePath(), "htn-http-cache");
    return new OkHttpClient.Builder()
//        .cache(new Cache(cache, cacheSize))
        .addNetworkInterceptor(new UserAgentInterceptor(userAgent))
        .addNetworkInterceptor(new RawResponseInterceptor())
        .addNetworkInterceptor(getUserAuthInterceptor())
        .build();
  }

  protected Gson getGson() {
    return new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(ListingResponse.class, new ListingResponseDeserializer())
        .registerTypeAdapter(Listing.class, new ListingDeserializer())
        .registerTypeAdapter(AbsComment.class, new CommentDeserializer())
        .create();
  }

  protected IRedditAuthService getAuthService() {
    return mRedditAuthService;
  }

  private Interceptor getUserAuthInterceptor() {
    return chain -> {
      Request originalRequest = chain.request();
      AccessToken token = mRedditAuthService.getAccessToken();
      Request newRequest = originalRequest.newBuilder()
          .removeHeader("Authorization")
          .addHeader("Authorization", "bearer " + token.getToken())
          .build();
      return chain.proceed(newRequest);
    };
  }

  public static <T> Func1<Response<T>, Observable<T>> responseToBody() {
    return response -> {
      if (!response.isSuccessful()) {
        return Observable.error(new HttpException(response));
      }
      return Observable.just(response.body());
    };
  }

  public static final class Builder {
    private String mBaseUrl = "https://oauth.reddit.com/";
    private String mBaseAuthUrl = "https://www.reddit.com/";
    private String mAppId;
    private String mRedirectUri;
    private String mDeviceId;
    private String mUserAgent;
    private AccessTokenManager mAccessTokenManager = AccessTokenManager.NONE;

    public Builder baseUrl(String baseUrl) {
      mBaseUrl = baseUrl;
      return this;
    }

    public Builder baseAuthUrl(String baseAuthUrl) {
      mBaseAuthUrl = baseAuthUrl;
      return this;
    }

    public Builder appId(String appId) {
      mAppId = appId;
      return this;
    }

    public Builder redirectUri(String redirectUri) {
      mRedirectUri = redirectUri;
      return this;
    }

    public Builder deviceId(String deviceId) {
      mDeviceId = deviceId;
      return this;
    }

    public Builder userAgent(String userAgent) {
      mUserAgent = userAgent;
      return this;
    }

    public Builder accessTokenManager(AccessTokenManager accessTokenManager) {
      mAccessTokenManager = accessTokenManager;
      return this;
    }

    public RedditService build() {
      if (mAppId == null) throw new IllegalStateException("app id must be set");
      if (mRedirectUri == null) throw new IllegalStateException("redirect uri must be set");
      if (mDeviceId == null) throw new IllegalStateException("device id must be set");
      if (mUserAgent == null) throw new IllegalStateException("user agent must be set");
      return new RedditService(
          mBaseUrl, mBaseAuthUrl, mAppId, mRedirectUri, mDeviceId, mUserAgent, mAccessTokenManager);
    }
  }
}
