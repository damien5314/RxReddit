package rxreddit.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rxreddit.model.AbsComment;
import rxreddit.model.AccessToken;
import rxreddit.model.Comment;
import rxreddit.model.Friend;
import rxreddit.model.FriendInfo;
import rxreddit.model.Listing;
import rxreddit.model.ListingResponse;
import rxreddit.model.ModReport;
import rxreddit.model.MoreChildrenResponse;
import rxreddit.model.ReportForm;
import rxreddit.model.SubmitPostResponse;
import rxreddit.model.Subreddit;
import rxreddit.model.SubredditRules;
import rxreddit.model.SubredditSidebar;
import rxreddit.model.UserAccessToken;
import rxreddit.model.UserIdentity;
import rxreddit.model.UserIdentityListing;
import rxreddit.model.UserReport;
import rxreddit.model.UserSettings;
import rxreddit.util.RxRedditUtil;

public class RedditService implements IRedditService {

    public static final String BASE_URL = "https://oauth.reddit.com";

    private RedditAPI api;
    private IRedditAuthService redditAuthService;

    protected RedditService(
            String baseUrl, String baseAuthUrl, String redditAppId, String redirectUri,
            String deviceId, String userAgent, AccessTokenManager atm, int cacheSizeBytes, File cacheFile,
            boolean loggingEnabled) {
        redditAuthService = new RedditAuthService(
                baseAuthUrl, redditAppId, redirectUri, deviceId, userAgent, atm, loggingEnabled);
        api = buildApi(baseUrl, userAgent, cacheSizeBytes, cacheFile, loggingEnabled);
    }

    @Override
    public String getRedirectUri() {
        return redditAuthService.getRedirectUri();
    }

    @Override
    public String getAuthorizationUrl() {
        return redditAuthService.getAuthorizationUrl();
    }

    @Override
    public boolean isUserAuthorized() {
        return redditAuthService.isUserAuthorized();
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
                    new IllegalStateException("Error during user authentication: " + params.get("error"))
            );
        }

        String authCode = params.get("code");
        if (authCode == null) {
            return Observable.error(new IllegalArgumentException("invalid callback url: " + callbackUrl));
        }

        String state = params.get("state");
        return redditAuthService.onAuthCodeReceived(authCode, state);
    }

    @Override
    public Observable<UserIdentity> getUserIdentity() {
        return requireUserAccessToken().flatMap(
                token -> api.getUserIdentity()
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Observable<UserSettings> getUserSettings() {
        return requireUserAccessToken().flatMap(
                token -> api.getUserSettings()
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Completable updateUserSettings(Map<String, String> settings) {
        String json = new Gson().toJson(settings);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        return requireUserAccessToken().flatMap(token ->
                api.updateUserSettings(body)
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    @Override
    public Observable<ListingResponse> getSubreddits(String where, String before, String after) {
        return requireUserAccessToken().flatMap(
                token -> api.getSubreddits(where, before, after)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Completable subscribe(String subreddit) {
        return requireUserAccessToken().flatMap(
                token -> api.subscribe(subreddit, true)
        ).flatMap(RxRedditUtil::checkResponse).ignoreElements();
    }

    @Override
    public Completable subscribe(Iterable<String> subreddits) {
        return requireUserAccessToken().flatMap(
                token -> api.subscribeAll(StringUtils.getCommaDelimitedString(subreddits), true)
        ).flatMap(RxRedditUtil::checkResponse).ignoreElements();
    }

    @Override
    public Completable unsubscribe(String subreddit) {
        return requireUserAccessToken().flatMap(
                token -> api.unsubscribe(subreddit)
        ).flatMap(RxRedditUtil::checkResponse).ignoreElements();
    }

    @Override
    public Completable unsubscribe(Iterable<String> subreddits) {
        return requireUserAccessToken().flatMap(
                token -> api.unsubscribeAll(StringUtils.getCommaDelimitedString(subreddits))
        ).flatMap(RxRedditUtil::checkResponse).ignoreElements();
    }

    @Override
    public Observable<ListingResponse> loadLinks(
            String subreddit, String sort, String timespan, String before, String after) {
        return requireAccessToken().flatMap(
                token -> {
                    String resolvedSort = sort != null ? sort : "hot";
                    return api.getLinks(resolvedSort, subreddit, timespan, before, after)
                            .flatMap(RxRedditUtil::responseToBody)
                            .flatMap((ListingResponse input) -> verifyLoadLinks(input, subreddit));
                }
        );
    }

    private static Observable<ListingResponse> verifyLoadLinks(ListingResponse input, String name) {
        final List<Listing> listings = input.getData().getChildren();
        final Listing listing = listings.get(0);
        if (!"t3".equals(listing.getKind())) {
            // Subreddit search returned, throw an error
            return Observable.error(new NoSuchSubredditException(name));
        } else {
            return Observable.just(input);
        }
    }

    @Override
    public Observable<List<ListingResponse>> loadLinkComments(
            String subreddit, String article, String sort, String commentId) {
        if (subreddit == null) {
            return Observable.error(new NullPointerException("subreddit == null"));
        }
        if (article == null) {
            return Observable.error(new NullPointerException("article == null"));
        }

        return requireAccessToken().flatMap(
                token -> api.getComments(subreddit, article, sort, commentId)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Observable<MoreChildrenResponse> loadMoreChildren(
            String linkId, List<String> childrenIds, String sort) {
        if (linkId == null) {
            return Observable.error(new NullPointerException("linkId == null"));
        }

        if (childrenIds == null || childrenIds.size() == 0) {
            return Observable.error(new NullPointerException("no comment IDs provided"));
        }

        return requireAccessToken().flatMap(
                token -> api.getMoreChildren("t3_" + linkId, StringUtils.join(",", childrenIds), sort)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Observable<UserIdentity> getUserInfo(String username) {
        return requireAccessToken().flatMap(
                token -> api.getUserInfo(username)
                        .flatMap(RxRedditUtil::responseToBody)
                        .map(UserIdentityListing::getUser)
        );
    }

    @Override
    public Observable<FriendInfo> getFriendInfo(String username) {
        return requireUserAccessToken().flatMap(
                token -> api.getFriendInfo(username)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Observable<List<Listing>> getUserTrophies(String username) {
        // TODO: This should return a List of Trophies instead of Listings
        if (username == null) {
            return Observable.error(new NullPointerException("username == null"));
        }

        return requireAccessToken().flatMap(
                token -> api.getUserTrophies(username)
                        .flatMap(RxRedditUtil::responseToBody)
                        .map(trophyResponse -> trophyResponse.getData().getTrophies())
        );
    }

    @Override
    public Observable<ListingResponse> loadUserProfile(
            String show, String username, String sort, String timespan, String before, String after) {
        if (show == null) {
            return Observable.error(new NullPointerException("show == null"));
        }

        if (username == null) {
            return Observable.error(new NullPointerException("username == null"));
        }

        return requireAccessToken().flatMap(
                token -> api.getUserProfile(show, username, sort, timespan, before, after)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Completable addFriend(String username) {
        if (username == null)
            return Completable.error(new NullPointerException("username == null"));
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{}");
        return requireUserAccessToken().flatMap(
                token -> api.addFriend(username, body)
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    @Override
    public Completable deleteFriend(String username) {
        return requireUserAccessToken().flatMap(
                token -> api.deleteFriend(username)
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    @Override
    public Completable saveFriendNote(String username, String note) {
        if (StringUtils.isEmpty(note)) {
            return Completable.error(new IllegalArgumentException("user note should be non-empty"));
        }

        String json = new Gson().toJson(new Friend(note));

        return requireUserAccessToken().flatMap(token ->
                api.addFriend(username, RequestBody.create(MediaType.parse("application/json"), json))
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    @Override
    public Observable<Subreddit> getSubredditInfo(String subreddit) {
        if (subreddit == null) {
            return Observable.error(new NullPointerException("subreddit == null"));
        }

        return requireAccessToken().flatMap(
                token -> api.getSubredditInfo(subreddit)
                        .flatMap(RxRedditUtil::responseToBody)
                        .flatMap(result -> verifyGetSubredditInfo(result, subreddit))
        );
    }

    private static Observable<Subreddit> verifyGetSubredditInfo(Subreddit input, String name) {
        if (!"t5".equals(input.getKind())) {
            // Subreddit search returned, throw an error
            return Observable.error(new NoSuchSubredditException(name));
        } else {
            return Observable.just(input);
        }
    }

    @Override
    public Observable<SubredditRules> getSubredditRules(String subreddit) {
        if (subreddit == null) {
            return Observable.error(new NullPointerException("subreddit == null"));
        }

        return requireAccessToken().flatMap(
                token -> api.getSubredditRules(subreddit)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Observable<SubredditSidebar> getSubredditSidebar(String subreddit) {
        if (subreddit == null) {
            return Observable.error(new NullPointerException("subreddit == null"));
        }

        // TODO Implement this once endpoint is fixed
        // https://github.com/reddit/reddit/pull/1424

        return requireAccessToken().flatMap(
                token -> api.getSubredditSidebar(subreddit)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Observable<List<ListingResponse>> getSubredditSticky(String subreddit) {
        if (subreddit == null) {
            return Observable.error(new NullPointerException("subreddit == null"));
        }

        return requireAccessToken().flatMap(
                token -> api.getSubredditSticky(subreddit)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Completable vote(String fullname, int direction) {
        if (fullname == null) {
            return Completable.error(new NullPointerException("fullname == null"));
        }

        return requireUserAccessToken().flatMap(token ->
                api.vote(fullname, direction)
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    @Override
    public Completable save(String fullname, String category, boolean toSave) {
        if (fullname == null) {
            return Completable.error(new NullPointerException("fullname == null"));
        }

        if (toSave) { // Save
            return requireUserAccessToken().flatMap(
                    token -> api.save(fullname, category)
                            .flatMap(RxRedditUtil::checkResponse)
            ).ignoreElements();
        } else { // Unsave
            return requireUserAccessToken().flatMap(
                    token -> api.unsave(fullname)
                            .flatMap(RxRedditUtil::checkResponse)
            ).ignoreElements();
        }
    }

    @Override
    public Completable hide(String fullname, boolean toHide) {
        if (fullname == null) {
            return Completable.error(new NullPointerException("fullname == null"));
        }

        if (toHide) { // Hide
            return requireUserAccessToken().flatMap(
                    token -> api.hide(fullname)
                            .flatMap(RxRedditUtil::checkResponse)
            ).ignoreElements();
        } else { // Unhide
            return requireUserAccessToken().flatMap(
                    token -> api.unhide(fullname)
                            .flatMap(RxRedditUtil::checkResponse)
            ).ignoreElements();
        }
    }

    @Override
    public Observable<ReportForm> getReportForm(String fullname) {
        if (fullname == null) {
            return Observable.error(new NullPointerException("id == null"));
        }

        return requireUserAccessToken().flatMap(
                token -> api.getReportForm(fullname)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Completable report(String id, String reason, String siteReason, String otherReason) {
        if (id == null) {
            return Completable.error(new NullPointerException("id == null"));
        }

        if (reason == null && siteReason == null && otherReason == null) {
            return Completable.error(new NullPointerException("no reason provided"));
        }

        return requireUserAccessToken().flatMap(
                token -> api.report(id, reason, siteReason, otherReason)
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    @Override
    public Observable<SubmitPostResponse> submit(
            String subreddit, String kind, String title, String url, String text,
            boolean sendReplies, boolean resubmit) {
        if (subreddit == null) {
            return Observable.error(new NullPointerException("subreddit == null"));
        }

        if (kind == null) {
            return Observable.error(new NullPointerException("kind == null"));
        }

        if (title == null) {
            return Observable.error(new NullPointerException("title == null"));
        }

        return requireUserAccessToken().flatMap(
                token -> api.submit(
                        subreddit, kind, title, url, text, sendReplies, resubmit, "json"
                )
                .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Observable<Comment> addComment(String parentId, String text) {
        if (parentId == null) {
            return Observable.error(new NullPointerException("parentId == null"));
        }

        if (text == null) {
            return Observable.error(new NullPointerException("text == null"));
        }

        return requireUserAccessToken().flatMap(
                token -> api.addComment(parentId, text)
                        .flatMap(RxRedditUtil::responseToBody)
                        .map(response -> {
                            List<String> errors = response.getErrors();

                            if (errors.size() > 0) {
                                throw new IllegalStateException("an error occurred: " + response.getErrors().get(0));
                            }

                            return response.getComment();
                        })
        );
    }

    @Override
    public Observable<ListingResponse> getInbox(
            String show,
            boolean markUnreads,
            String before,
            String after) {
        if (show == null) {
            return Observable.error(new NullPointerException("show == null"));
        }

        return requireUserAccessToken().flatMap(
                token -> api.getInbox(show, markUnreads, before, after)
                        .flatMap(RxRedditUtil::responseToBody)
        );
    }

    @Override
    public Completable markAllMessagesRead() {
        return requireUserAccessToken().flatMap(
                token -> api.markAllMessagesRead()
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    @Override
    public Completable markMessagesRead(String commaSeparatedFullnames) {
        // TODO: This should take a List of message fullnames and construct the parameter
        return requireUserAccessToken().flatMap(
                token -> api.markMessagesRead(commaSeparatedFullnames)
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    @Override
    public Completable markMessagesUnread(String commaSeparatedFullnames) {
        // TODO: This should take a List of message fullnames and construct the parameter
        return requireUserAccessToken().flatMap(
                token -> api.markMessagesUnread(commaSeparatedFullnames)
                        .flatMap(RxRedditUtil::checkResponse)
        ).ignoreElements();
    }

    @Override
    public Completable revokeAuthentication() {
        return redditAuthService.revokeUserAuthentication();
    }

    protected Observable<AccessToken> requireAccessToken() {
        return redditAuthService.refreshAccessToken();
    }

    protected Observable<UserAccessToken> requireUserAccessToken() {
        return redditAuthService.refreshUserAccessToken();
    }

    private RedditAPI buildApi(String baseUrl, String userAgent, int cacheSizeBytes, File cachePath, boolean loggingEnabled) {
        Retrofit restAdapter = new Retrofit.Builder()
                .client(getOkHttpClient(userAgent, cacheSizeBytes, cachePath, loggingEnabled))
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return restAdapter.create(RedditAPI.class);
    }

    protected OkHttpClient getOkHttpClient(String userAgent, int cacheSizeBytes, File cachePath, boolean loggingEnabled) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(new UserAgentInterceptor(userAgent))
                .addNetworkInterceptor(new RawResponseInterceptor())
                .addNetworkInterceptor(getUserAuthInterceptor());

        if (cacheSizeBytes > 0) {
            builder.cache(new Cache(cachePath, cacheSizeBytes));
        }

        if (loggingEnabled) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }

        return builder.build();
    }

    public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(ListingResponse.class, new ListingResponseDeserializer())
                .registerTypeAdapter(Listing.class, new ListingDeserializer())
                .registerTypeAdapter(AbsComment.class, new CommentDeserializer())
                .registerTypeAdapter(UserReport.class, new UserReportDeserializer())
                .registerTypeAdapter(ModReport.class, new ModReportDeserializer())
                .create();
    }

    protected IRedditAuthService getAuthService() {
        return redditAuthService;
    }

    private Interceptor getUserAuthInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            AccessToken token = redditAuthService.getAccessToken();
            Request newRequest = originalRequest.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "bearer " + token.getToken())
                    .build();
            return chain.proceed(newRequest);
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
        private int mCacheSizeBytes = 0;
        private File mCacheFile = null;
        private boolean mLoggingEnabled = false;

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

        public Builder cache(int sizeBytes, File path) {
            mCacheSizeBytes = sizeBytes;
            mCacheFile = path;
            return this;
        }

        public Builder loggingEnabled(boolean enabled) {
            mLoggingEnabled = enabled;
            return this;
        }

        public RedditService build() {
            if (mAppId == null) throw new IllegalStateException("app id must be set");
            if (mRedirectUri == null) throw new IllegalStateException("redirect uri must be set");
            if (mDeviceId == null) throw new IllegalStateException("device id must be set");
            if (mUserAgent == null) throw new IllegalStateException("user agent must be set");
            return new RedditService(
                    mBaseUrl, mBaseAuthUrl, mAppId, mRedirectUri, mDeviceId, mUserAgent,
                    mAccessTokenManager, mCacheSizeBytes, mCacheFile, mLoggingEnabled);
        }
    }
}
