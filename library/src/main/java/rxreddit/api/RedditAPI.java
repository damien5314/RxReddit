package rxreddit.api;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rxreddit.model.AddCommentResponse;
import rxreddit.model.FriendInfo;
import rxreddit.model.ListingResponse;
import rxreddit.model.MoreChildrenResponse;
import rxreddit.model.Subreddit;
import rxreddit.model.SubredditRules;
import rxreddit.model.SubredditSidebar;
import rxreddit.model.TrophyResponse;
import rxreddit.model.UserIdentity;
import rxreddit.model.UserIdentityListing;
import rxreddit.model.UserSettings;

interface RedditAPI {

    @GET("/api/info")
    Observable<Response<ListingResponse>> getInfo(
            @Query("id") String id
    );

    @GET("/r/{subreddit}/api/info")
    Observable<Response<ListingResponse>> getInfo(
            @Path("subreddit") String subreddit,
            @Query("id") String id
    );

    @GET("/r/{subreddit}/about")
    Observable<Response<Subreddit>> getSubredditInfo(
            @Path("subreddit") String subreddit
    );

    @GET("/r/{subreddit}/about/rules")
    Observable<Response<SubredditRules>> getSubredditRules(
            @Path("subreddit") String subreddit
    );

    @GET("/r/{subreddit}/about/sidebar")
    Observable<Response<SubredditSidebar>> getSubredditSidebar(
            @Path("subreddit") String subreddit
    );

    @GET("/r/{subreddit}/about/sticky")
    Observable<Response<List<ListingResponse>>> getSubredditSticky(
            @Path("subreddit") String subreddit
    );

    @GET("/api/v1/me")
    Observable<Response<UserIdentity>> getUserIdentity();

    @GET("/api/v1/me/prefs")
    Observable<Response<UserSettings>> getUserSettings();

    @PATCH("/api/v1/me/prefs")
    Observable<Response<Void>> updateUserSettings(
            @Body RequestBody json
    );

    @GET("/subreddits/mine/{where}")
    Observable<Response<ListingResponse>> getSubreddits(
            @Path("where") String where, @Query("before") String before, @Query("after") String after
    );

    @POST("/api/subscribe?action=sub")
    Observable<Response<Void>> subscribe(
            @Query("sr_name") String subreddit,
            @Query("skip_initial_defaults") Boolean skipInitialDefaults
    );

    @POST("/api/subscribe?action=sub")
    Observable<Response<Void>> subscribeAll(
            @Query("sr_name") String subreddit,
            @Query("skip_initial_defaults") Boolean skipInitialDefaults
    );

    @POST("/api/subscribe?action=unsub")
    Observable<Response<Void>> unsubscribe(
            @Query("sr_name") String subreddit
    );

    @POST("/api/subscribe?action=unsub")
    Observable<Response<Void>> unsubscribeAll(
            @Query("sr_name") String subreddit
    );

    @GET("/{sort}.json")
    Observable<Response<ListingResponse>> getLinks(
            @Path("sort") String sort,
            @Query("r") String subreddit,
            @Query("t") String timespan,
            @Query("before") String before,
            @Query("after") String after
    );

    @GET("/r/{subreddit}/comments/{articleId}.json")
    Observable<Response<List<ListingResponse>>> getComments(
            @Path("subreddit") String subreddit,
            @Path("articleId") String articleId,
            @Query("sort") String sort,
            @Query("comment") String commentId
    );

    @GET("/api/morechildren?api_type=json")
    Observable<Response<MoreChildrenResponse>> getMoreChildren(
            @Query("link_id") String linkId,
            @Query("children") String children,
            @Query("sort") String sort
    );

    @GET("/user/{username}/{show}")
    Observable<Response<ListingResponse>> getUserProfile(
            @Path("show") String show,
            @Path("username") String username,
            @Query("sort") String sort,
            @Query("t") String timespan,
            @Query("before") String before,
            @Query("after") String after
    );

    @GET("/user/{username}/about")
    Observable<Response<UserIdentityListing>> getUserInfo(
            @Path("username") String username
    );

    @GET("/api/v1/me/friends/{username}")
    Observable<Response<FriendInfo>> getFriendInfo(
            @Path("username") String username
    );

    @GET("/api/v1/user/{username}/trophies")
    Observable<Response<TrophyResponse>> getUserTrophies(
            @Path("username") String username
    );

    @POST("/api/vote")
    Observable<Response<Void>> vote(
            @Query("id") String id,
            @Query("dir") int dir
    );

    @POST("/api/save")
    Observable<Response<Void>> save(
            @Query("id") String id,
            @Query("category") String category
    );

    @POST("/api/unsave")
    Observable<Response<Void>> unsave(
            @Query("id") String id
    );

    @POST("/api/hide")
    Observable<Response<Void>> hide(
            @Query("id") String id
    );

    @POST("/api/unhide")
    Observable<Response<Void>> unhide(
            @Query("id") String id
    );

    @POST("/api/report?api_type=json")
    Observable<Response<Void>> report(
            @Query("thing_id") String fullname,
            @Query("reason") String reason,
            @Query("site_reason") String siteReason,
            @Query("other_reason") String otherReason
    );

    @FormUrlEncoded
    @POST("/api/comment?api_type=json")
    Observable<Response<AddCommentResponse>> addComment(
            @Field("parent") String parentId,
            @Field("text") String commentText
    );

    @PUT("/api/v1/me/friends/{username}")
    Observable<Response<Void>> addFriend(
            @Path("username") String username,
            @Body RequestBody json
    );

    @DELETE("/api/v1/me/friends/{username}")
    Observable<Response<Void>> deleteFriend(
            @Path("username") String username
    );

    @GET("/message/{show}")
    Observable<Response<ListingResponse>> getInbox(
            @Path("show") String show,
            @Query("before") String before,
            @Query("after") String after
    );

    @POST("/api/read_all_messages")
    Observable<Response<Void>> markAllMessagesRead();

    @POST("/api/read_message")
    Observable<Response<Void>> markMessagesRead(
            @Query("id") String commaSeparatedFullnames
    );

    @POST("/api/unread_message")
    Observable<Response<Void>> markMessagesUnread(
            @Query("id") String commaSeparatedFullnames
    );
}