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
import rxreddit.RxRedditUtil;
import rxreddit.model.AddCommentResponse;
import rxreddit.model.Comment;
import rxreddit.model.FriendInfo;
import rxreddit.model.Listing;
import rxreddit.model.ListingResponse;
import rxreddit.model.MoreChildrenResponse;
import rxreddit.model.ReportForm;
import rxreddit.model.SubmitPostResponse;
import rxreddit.model.Subreddit;
import rxreddit.model.SubredditRules;
import rxreddit.model.SubredditSidebar;
import rxreddit.model.TrophyResponse;
import rxreddit.model.UserAccessToken;
import rxreddit.model.UserIdentity;
import rxreddit.model.UserSettings;

public class RedditServiceMock extends RedditService {

    private Gson mGson = getGson();

    // Mock the authentication state
    private boolean mAuthorized = false;

    public RedditServiceMock() {
        super(
                "http://127.0.0.1/",
                "http://127.0.0.1/",
                "AmkOVyT8Zl5ZIg", // fake app id
                "http://127.0.0.1/", // redirect uri
                "dd076025-1631-49a6-b52f-612ba75a4023", // random UUID for device ID
                RxRedditUtil.getUserAgent("java", "rxreddit", "0.1", "damien5314"),
                AccessTokenManager.NONE, 0, null, true);
    }

    @Override
    public boolean isUserAuthorized() {
        return mAuthorized;
    }

    @Override
    public Observable<UserAccessToken> processAuthenticationCallback(String callbackUrl) {
        mAuthorized = true;
        UserAccessToken response = new UserAccessToken();
        response.setToken("1234567-ADmpkqak1aTo71ABCDECvAXiXGk");
        response.setTokenType("bearer");
        // Set expiration of token to 1 hour from now
        response.setExpiration(System.currentTimeMillis() + 3600);
        response.setRefreshToken("1234567-_dYROLO4pgfABCDEr1jm-12345");
        response.setScope("account history identity mysubreddits privatemessages read report save " +
                "submit subscribe vote");
        return Observable.just(response);
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
        return Observable.just(null);
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
        Type listType = new TypeToken<List<ListingResponse>>() {}.getType();
        List<ListingResponse> response = mGson.fromJson(
                getReaderForFile("link_comments.json"), listType);
        return Observable.just(response);
    }

    @Override
    public Observable<MoreChildrenResponse> loadMoreChildren(
            String linkId, List<String> childrenIds, String sort) {
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
        return Observable.just(null);
    }

    @Override
    public Observable<Void> deleteFriend(String username) {
        return Observable.just(null);
    }

    @Override
    public Observable<Void> saveFriendNote(String username, String note) {
        return Observable.just(null);
    }

    @Override
    public Observable<Subreddit> getSubredditInfo(String subreddit) {
        Subreddit response = mGson.fromJson(
                getReaderForFile("subreddit_info.json"), Subreddit.class
        );
        return Observable.just(response);
    }

    @Override
    public Observable<SubredditRules> getSubredditRules(String subreddit) {
        SubredditRules response = mGson.fromJson(
                getReaderForFile("GET_subreddit_about_rules.json"), SubredditRules.class
        );
        return Observable.just(response);
    }

    @Override
    public Observable<SubredditSidebar> getSubredditSidebar(String subreddit) {
        // TODO Implement this once endpoint is fixed
        // https://github.com/reddit/reddit/pull/1424

        throw new UnsupportedOperationException(
                "this endpoint is broken: https://github.com/reddit/reddit/pull/1424"
        );
    }

    @Override
    public Observable<List<ListingResponse>> getSubredditSticky(String subreddit) {
        Type listType = new TypeToken<List<ListingResponse>>() {}.getType();
        List<ListingResponse> response = mGson.fromJson(
                getReaderForFile("GET_subreddit_about_sticky.json"), listType
        );
        return Observable.just(response);
    }

    @Override
    public Observable<Void> vote(String fullname, int direction) {
        return Observable.just(null);
    }

    @Override
    public Observable<Void> save(String fullname, String category, boolean toSave) {
        return Observable.just(null);
    }

    @Override
    public Observable<Void> hide(String fullname, boolean toHide) {
        return Observable.just(null);
    }

    @Override
    public Observable<ReportForm> getReportForm(String fullname) {
        return Observable.error(new UnsupportedOperationException());
    }

    @Override
    public Observable<Void> report(String id, String reason, String siteReason, String otherReason) {
        return Observable.error(new UnsupportedOperationException());
    }

    @Override
    public Observable<SubmitPostResponse> submit(
            String subreddit, String kind, String title, String url, String text,
            boolean sendReplies, boolean resubmit) {
        SubmitPostResponse response = mGson.fromJson(
                getReaderForFile("POST_submit.json"), SubmitPostResponse.class
        );
        return Observable.just(response);
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
        return Observable.just(null);
    }

    @Override
    public Observable<Void> markMessagesRead(String commaSeparatedFullnames) {
        return Observable.just(null);
    }

    @Override
    public Observable<Void> markMessagesUnread(String commaSeparatedFullnames) {
        return Observable.just(null);
    }

    @Override
    public Observable<ListingResponse> getSubreddits(String where, String before, String after) {
        ListingResponse response = mGson.fromJson(
                getReaderForFile("GET_subreddits_mine_subscriber.json"), ListingResponse.class);
        return Observable.just(response);
    }

    @Override
    public Observable<Void> subscribe(String subreddit) {
        return Observable.just(null);
    }

    @Override
    public Observable<Void> subscribe(Iterable<String> subreddits) {
        return Observable.just(null);
    }

    @Override
    public Observable<Void> unsubscribe(String subreddit) {
        return Observable.just(null);
    }

    @Override
    public Observable<Void> unsubscribe(Iterable<String> subreddits) {
        return Observable.just(null);
    }

    @Override
    public Observable<Void> revokeAuthentication() {
        return Observable.just(null);
    }

    private Reader getReaderForFile(String filename) {
//    InputStream is;
        try {
            Class clazz = getClass();
            ClassLoader cl = clazz.getClassLoader();
//      URL url = clazz.getResource("/mock/" + filename);
//      URL url = cl.getResource("mock/" + filename);
//      String path = url.getFile();
//      File file = new File(url.toURI());
//      return new FileReader(url.getPath());
            InputStream ins = cl.getResourceAsStream("mock/" + filename); // works for sample app
//      InputStream ins = cl.getResourceAsStream("/mock/" + filename); // does not work for sample app
//      InputStream ins = clazz.getResourceAsStream("mock/" + filename); // does not work for sample app
//      InputStream ins = clazz.getResourceAsStream("/mock/" + filename); // works for sample app
            return new InputStreamReader(ins);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
//    return new InputStreamReader(is);
    }
}
