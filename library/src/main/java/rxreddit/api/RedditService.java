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
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rxreddit.Util;
import rxreddit.model.AbsComment;
import rxreddit.model.AccessToken;
import rxreddit.model.Comment;
import rxreddit.model.CommentStub;
import rxreddit.model.Friend;
import rxreddit.model.FriendInfo;
import rxreddit.model.Hideable;
import rxreddit.model.Link;
import rxreddit.model.Listing;
import rxreddit.model.ListingResponse;
import rxreddit.model.MoreChildrenResponse;
import rxreddit.model.Savable;
import rxreddit.model.Subreddit;
import rxreddit.model.UserAccessToken;
import rxreddit.model.UserIdentity;
import rxreddit.model.UserSettings;
import rxreddit.model.Votable;

final public class RedditService implements IRedditService {
  
  private RedditAPI mAPI;
  private IRedditAuthService mRedditAuthService;

  public RedditService(
      String redditAppId, String redirectUri, String deviceId, String userAgent,
      AccessTokenManager atm) {
    mRedditAuthService = new RedditAuthService(redditAppId, redirectUri, deviceId, userAgent, atm);
    mAPI = buildApi(userAgent);
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
    Map<String, String> params = Util.getQueryParametersFromUrl(callbackUrl);
    if (params.containsKey("error")) {
      return Observable.error(
          new RuntimeException("User declined to authenticate application"));
    }
    String authCode = params.get("code");
    String state = params.get("state");
    return mRedditAuthService.onAuthCodeReceived(authCode, state);
  }

  @Override
  public Observable<UserIdentity> getUserIdentity() {
    return requireUserAccessToken().flatMap(token ->
        mAPI.getUserIdentity()
            .map(Response::body));
  }

  @Override
  public Observable<UserSettings> getUserSettings() {
    return requireUserAccessToken().flatMap(token ->
        mAPI.getUserSettings()
            .map(Response::body));
  }

  @Override
  public Observable<Void> updateUserSettings(Map<String, String> settings) {
    String json = new Gson().toJson(settings);
    RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
    return requireUserAccessToken().flatMap(token ->
        mAPI.updateUserSettings(body));
  }

  @Override
  public Observable<ListingResponse> loadLinks(
      String subreddit, String sort, String timespan, String before, String after) {
    return requireAccessToken().flatMap(token ->
        mAPI.getLinks(sort, subreddit, timespan, before, after)
            .map(Response::body));
  }

  @Override
  public Observable<List<ListingResponse>> loadLinkComments(
      String subreddit, String article, String sort, String commentId) {
    return requireAccessToken().flatMap(token ->
        mAPI.getComments(subreddit, article, sort, commentId)
            .map(Response::body));
  }

  @Override
  public Observable<MoreChildrenResponse> loadMoreChildren(
      Link link, CommentStub parentStub, List<String> childrenIds, String sort) {
    return requireAccessToken().flatMap(token ->
        mAPI.getMoreChildren(link.getFullName(), Util.join(",", childrenIds), sort)
            .map(Response::body));
  }

  @Override
  public Observable<UserIdentity> getUserInfo(String username) {
    return requireAccessToken().flatMap(token ->
        mAPI.getUserInfo(username)
            .map(response -> response.body().getUser()));
  }

  @Override
  public Observable<FriendInfo> getFriendInfo(String username) {
    return requireUserAccessToken().flatMap(token ->
        mAPI.getFriendInfo(username)
            .map(Response::body));
  }

  @Override
  public Observable<List<Listing>> getUserTrophies(String username) {
    return requireAccessToken().flatMap(token ->
        mAPI.getUserTrophies(username)
            .map(response -> response.body().getData().getTrophies()));
  }

  @Override
  public Observable<ListingResponse> loadUserProfile(
      String show, String username, String sort, String timespan, String before, String after) {
    return requireAccessToken().flatMap(token ->
        mAPI.getUserProfile(show, username, sort, timespan, before, after)
            .map(Response::body));
  }

  @Override
  public Observable<Void> addFriend(String username) {
    String json = "{}";
    RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
    return requireUserAccessToken().flatMap(token ->
        mAPI.addFriend(username, body));
  }

  @Override
  public Observable<Void> deleteFriend(String username) {
    return requireUserAccessToken().flatMap(token ->
        mAPI.deleteFriend(username));
  }

  @Override
  public Observable<Void> saveFriendNote(String username, String note) {
    if (Util.isEmpty(note)) {
      return Observable.error(new RuntimeException("User note should be non-empty"));
    }
    String json = new Gson().toJson(new Friend(note));
    return requireUserAccessToken().flatMap(token ->
        mAPI.addFriend(username, RequestBody.create(MediaType.parse("application/json"), json)));
  }

  @Override
  public Observable<Subreddit> getSubredditInfo(String subreddit) {
    return requireAccessToken().flatMap(token ->
        mAPI.getSubredditInfo(subreddit)
            .map(Response::body));
  }

  @Override
  public Observable<Void> vote(Votable listing, int direction) {
    String fullname = ((Listing) listing).getFullName();
    return requireUserAccessToken().flatMap(token ->
        mAPI.vote(fullname, direction));
  }

  @Override
  public Observable<Void> save(
      Savable listing, String category, boolean toSave) {
    if (toSave) { // Save
      return requireUserAccessToken().flatMap(token ->
          mAPI.save(listing.getFullName(), category));
    } else { // Unsave
      return requireUserAccessToken().flatMap(token ->
          mAPI.unsave(listing.getFullName()));
    }
  }

  @Override
  public Observable<Void> hide(Hideable listing, boolean toHide) {
    if (toHide) { // Hide
      return requireUserAccessToken().flatMap(token ->
          mAPI.hide(listing.getFullName()));
    } else { // Unhide
      return requireUserAccessToken().flatMap(token ->
          mAPI.unhide(listing.getFullName()));
    }
  }

  @Override
  public Observable<Void> report(String id, String reason) {
    return Observable.error(new UnsupportedOperationException());
  }

  @Override
  public Observable<Comment> addComment(String parentId, String text) {
    return requireUserAccessToken().flatMap(token ->
        mAPI.addComment(parentId, text)
            .map(response -> response.body().getComment()));
  }

  @Override
  public Observable<ListingResponse> getInbox(String show, String before, String after) {
    return requireAccessToken().flatMap(token ->
        mAPI.getInbox(show, before, after));
  }

  @Override
  public Observable<Void> markAllMessagesRead() {
    return requireAccessToken().flatMap(token ->
        mAPI.markAllMessagesRead());
  }

  @Override
  public Observable<Void> markMessagesRead(String commaSeparatedFullnames) {
    return requireAccessToken().flatMap(token ->
        mAPI.markMessagesRead(commaSeparatedFullnames));
  }

  @Override
  public Observable<Void> markMessagesUnread(String commaSeparatedFullnames) {
    return requireAccessToken().flatMap(token ->
        mAPI.markMessagesUnread(commaSeparatedFullnames));
  }

  @Override
  public Observable<Void> revokeAuthentication() {
    return mRedditAuthService.revokeAuthentication();
  }

  private Observable<AccessToken> requireAccessToken() {
    return mRedditAuthService.refreshAccessToken();
  }

  private Observable<UserAccessToken> requireUserAccessToken() {
    return mRedditAuthService.refreshUserAccessToken();
  }

  private RedditAPI buildApi(String userAgent) {
//    final int cacheSize = 10 * 1024 * 1024; // 10 MiB
//    File cache = new File(
//        HoldTheNarwhal.getContext().getCacheDir().getAbsolutePath(), "htn-http-cache");
    OkHttpClient client = new OkHttpClient.Builder()
//        .cache(new Cache(cache, cacheSize))
        .addNetworkInterceptor(new UserAgentInterceptor(userAgent))
        .addNetworkInterceptor(new RawResponseInterceptor())
        .addNetworkInterceptor(getUserAuthInterceptor())
        .build();
    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .registerTypeAdapter(ListingResponse.class, new ListingResponseDeserializer())
        .registerTypeAdapter(Listing.class, new ListingDeserializer())
        .registerTypeAdapter(AbsComment.class, new CommentDeserializer())
        .create();
    Retrofit restAdapter = new Retrofit.Builder()
        .client(client)
        .baseUrl("https://oauth.reddit.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return restAdapter.create(RedditAPI.class);
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
}
