package rxreddit.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
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

    Completable updateUserSettings(Map<String, String> settings);

    Observable<ListingResponse> getSubreddits(String where, String before, String after);

    Completable subscribe(String subreddit);

    Completable subscribe(Iterable<String> subreddits);

    Completable unsubscribe(String subreddit);

    Completable unsubscribe(Iterable<String> subreddits);

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

    Completable addFriend(String username);

    Completable deleteFriend(String username);

    Completable saveFriendNote(String username, String note);

    Observable<Subreddit> getSubredditInfo(String subreddit);

    Observable<SubredditRules> getSubredditRules(String subreddit);

    Observable<SubredditSidebar> getSubredditSidebar(String subreddit);

    Observable<List<ListingResponse>> getSubredditSticky(String subreddit);

    Completable vote(String fullname, int direction);

    Completable save(String fullname, String category, boolean toSave);

    Completable hide(String fullname, boolean toHide);

    Observable<ReportForm> getReportForm(String fullname);

    Completable report(String fullname, String reason, String siteReason, String otherReason);

    Observable<SubmitPostResponse> submit(
            String subreddit, String kind, String title, String url, String text,
            boolean sendReplies, boolean resubmit
    );

    Observable<Comment> addComment(String parentId, String text);

    Observable<ListingResponse> getInbox(String show, boolean markRead, String before, String after);

    Completable markAllMessagesRead();

    Completable markMessagesRead(String commaSeparatedFullnames);

    Completable markMessagesUnread(String commaSeparatedFullnames);

    Completable revokeAuthentication();
}
