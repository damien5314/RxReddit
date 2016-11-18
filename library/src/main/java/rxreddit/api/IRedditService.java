package rxreddit.api;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rxreddit.model.Comment;
import rxreddit.model.FriendInfo;
import rxreddit.model.Listing;
import rxreddit.model.ListingResponse;
import rxreddit.model.MoreChildrenResponse;
import rxreddit.model.ReportForm;
import rxreddit.model.Subreddit;
import rxreddit.model.SubredditRules;
import rxreddit.model.SubredditSidebar;
import rxreddit.model.UserAccessToken;
import rxreddit.model.UserIdentity;
import rxreddit.model.UserSettings;

interface IRedditService {

    String getRedirectUri();

    String getAuthorizationUrl();

    boolean isUserAuthorized();

    Observable<UserAccessToken> processAuthenticationCallback(String callbackUrl);

    Observable<UserIdentity> getUserIdentity();

    Observable<UserSettings> getUserSettings();

    Observable<Void> updateUserSettings(Map<String, String> settings);

    Observable<ListingResponse> getSubreddits(String where, String before, String after);

    Observable<Void> subscribe(String subreddit);

    Observable<Void> subscribe(Iterable<String> subreddits);

    Observable<Void> unsubscribe(String subreddit);

    Observable<Void> unsubscribe(Iterable<String> subreddits);

    Observable<ListingResponse> loadLinks(
            String subreddit, String sort, String timespan, String before, String after
    );

    Observable<List<ListingResponse>> loadLinkComments(
            String subreddit, String article, String sort, String commentId
    );

    Observable<MoreChildrenResponse> loadMoreChildren(
            String linkId, List<String> childrenIds, String sort
    );

    Observable<UserIdentity> getUserInfo(String username);

    Observable<FriendInfo> getFriendInfo(String username);

    Observable<List<Listing>> getUserTrophies(String username);

    Observable<ListingResponse> loadUserProfile(
            String show, String username, String sort, String timespan, String before, String after
    );

    Observable<Void> addFriend(String username);

    Observable<Void> deleteFriend(String username);

    Observable<Void> saveFriendNote(String username, String note);

    Observable<Subreddit> getSubredditInfo(String subreddit);

    Observable<SubredditRules> getSubredditRules(String subreddit);

    Observable<SubredditSidebar> getSubredditSidebar(String subreddit);

    Observable<List<ListingResponse>> getSubredditSticky(String subreddit);

    Observable<Void> vote(String fullname, int direction);

    Observable<Void> save(String fullname, String category, boolean toSave);

    Observable<Void> hide(String fullname, boolean toHide);

    Observable<ReportForm> getReportForm(String fullname);

    Observable<Void> report(String fullname, String reason, String siteReason, String otherReason);

    Observable<Comment> addComment(String parentId, String text);

    Observable<ListingResponse> getInbox(String show, String before, String after);

    Observable<Void> markAllMessagesRead();

    Observable<Void> markMessagesRead(String commaSeparatedFullnames);

    Observable<Void> markMessagesUnread(String commaSeparatedFullnames);

    Observable<Void> revokeAuthentication();
}
