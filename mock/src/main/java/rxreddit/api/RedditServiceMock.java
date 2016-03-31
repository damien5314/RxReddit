package rxreddit.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rxreddit.model.AddCommentResponse;
import rxreddit.model.Comment;
import rxreddit.model.CommentStub;
import rxreddit.model.FriendInfo;
import rxreddit.model.Hideable;
import rxreddit.model.Link;
import rxreddit.model.Listing;
import rxreddit.model.ListingResponse;
import rxreddit.model.MoreChildrenResponse;
import rxreddit.model.Savable;
import rxreddit.model.Subreddit;
import rxreddit.model.TrophyResponse;
import rxreddit.model.UserAccessToken;
import rxreddit.model.UserIdentity;
import rxreddit.model.UserSettings;
import rxreddit.model.Votable;

public class RedditServiceMock extends RedditService {

  private Gson mGson = getGson();

  // Mock the authentication state
  private boolean mAuthorized = false;

  public RedditServiceMock(
      String redditAppId, String redirectUri, String deviceId, String userAgent,
      AccessTokenManager atm) {
    super(redditAppId, redirectUri, deviceId, userAgent, atm);
  }

  @Override
  public String getRedirectUri() {
    // real implementation is fine
    return super.getRedirectUri();
  }

  @Override
  public String getAuthorizationUrl() {
    // real implementation is fine
    return super.getAuthorizationUrl();
  }

  @Override
  public boolean isUserAuthorized() {
    return mAuthorized;
  }

  @Override
  public Observable<UserAccessToken> processAuthenticationCallback(String callbackUrl) {
    return null;
  }

  @Override
  public Observable<UserIdentity> getUserIdentity() {
    UserIdentity response = mGson.fromJson(
        getReaderForFile("user_identity.json"), UserIdentity.class);
    return Observable.just(response);
  }

  @Override
  public Observable<UserSettings> getUserSettings() {
    UserSettings response = mGson.fromJson(
        getReaderForFile("user_settings.json"), UserSettings.class);
    return Observable.just(response);
  }

  @Override
  public Observable<Void> updateUserSettings(Map<String, String> settings) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<ListingResponse> loadLinks(
      String subreddit, String sort, String timespan, String before, String after) {
    ListingResponse response = mGson.fromJson(
        getReaderForFile("all_subreddits.json"), ListingResponse.class);
    return Observable.just(response);
  }

  @Override
  public Observable<List<ListingResponse>> loadLinkComments(
      String subreddit, String article,
      String sort, String commentId) {
    Type listType = new TypeToken<List<ListingResponse>>(){}.getType();
    List<ListingResponse> response = mGson.fromJson(
        getReaderForFile("link_comments.json"), listType);
    return Observable.just(response);
  }

  @Override
  public Observable<MoreChildrenResponse> loadMoreChildren(
      Link link, CommentStub moreComments,
      List<String> children, String sort) {
    MoreChildrenResponse response = mGson.fromJson(
        getReaderForFile("more_comments.json"), MoreChildrenResponse.class);
    return Observable.just(response);
  }

  @Override
  public Observable<UserIdentity> getUserInfo(String username) {
    UserIdentity response = mGson.fromJson(
        getReaderForFile("user_identity.json"), UserIdentity.class);
    return Observable.just(response);
  }

  @Override
  public Observable<FriendInfo> getFriendInfo(String username) {
    FriendInfo response = mGson.fromJson(
        getReaderForFile("user_identity_friend_info.json"), FriendInfo.class);
    return Observable.just(response);
  }

  @Override
  public Observable<List<Listing>> getUserTrophies(String username) {
    TrophyResponse response = mGson.fromJson(
        getReaderForFile("user_identity_friend_trophies.json"), TrophyResponse.class);
    return Observable.just(response)
        .map(response2 -> response2.getData().getTrophies());
  }

  @Override
  public Observable<ListingResponse> loadUserProfile(
      String show, String username, String sort, String timespan, String before, String after) {
    ListingResponse response = mGson.fromJson(
        getReaderForFile("user_profile_" + show + ".json"), ListingResponse.class);
    return Observable.just(response);
  }

  @Override
  public Observable<Void> addFriend(String username) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Void> deleteFriend(String username) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Void> saveFriendNote(String username, String note) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Subreddit> getSubredditInfo(String subreddit) {
    Subreddit response = mGson.fromJson(
        getReaderForFile("subreddit_info.json"), Subreddit.class);
    return Observable.just(response);
  }

  @Override
  public Observable<Void> vote(Votable votable, int direction) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Void> save(
      Savable listing, String category, boolean save) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Void> hide(Hideable listing, boolean toHide) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Void> report(String id, String reason) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Comment> addComment(String parentId, String text) {
    AddCommentResponse response = mGson.fromJson(
        getReaderForFile("add_comment.json"), AddCommentResponse.class);
    return Observable.just(response)
        .map(AddCommentResponse::getComment);
  }

  @Override
  public Observable<ListingResponse> getInbox(String show, String after, String before) {
    ListingResponse response = mGson.fromJson(
        getReaderForFile("inbox_" + show + ".json"), ListingResponse.class);
    return Observable.just(response);
  }

  @Override
  public Observable<Void> markAllMessagesRead() {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Void> markMessagesRead(String commaSeparatedFullnames) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Void> markMessagesUnread(String commaSeparatedFullnames) {
    return Observable.just((Void) null);
  }

  @Override
  public Observable<Void> revokeAuthentication() {
    return null;
  }

  private Reader getReaderForFile(String filename) {
    InputStream is;
    is = RedditServiceMock.class.getClassLoader().getResourceAsStream("api/" + filename);
    return new InputStreamReader(is);
  }
}
