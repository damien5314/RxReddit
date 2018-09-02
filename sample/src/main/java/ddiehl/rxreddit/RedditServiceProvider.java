package ddiehl.rxreddit;

import android.content.Context;

import ddiehl.rxreddit.sample.BuildConfig;
import rxreddit.android.AndroidAccessTokenManager;
import rxreddit.android.AndroidUtil;
import rxreddit.api.RedditService;
import rxreddit.util.RxRedditUtil;

public final class RedditServiceProvider {

    private static RedditService _instance;

    private RedditServiceProvider() {
    }

    public static RedditService get(Context context) {
        if (_instance == null) {
            synchronized (RedditServiceProvider.class) {
                if (_instance == null) {
//                    _instance = new RedditServiceMock();
                    _instance = new RedditService.Builder()
                            .baseUrl("https://oauth.reddit.com")
                            .baseAuthUrl("https://www.reddit.com")
                            .appId(BuildConfig.REDDIT_APP_ID)
                            .redirectUri(BuildConfig.REDDIT_REDIRECT_URI)
                            .deviceId(AndroidUtil.getDeviceId(context))
                            .userAgent(RxRedditUtil.getUserAgent(
                                    "android",
                                    "ddiehl.rxreddit.sampleapp",
                                    BuildConfig.VERSION_NAME,
                                    "damien5314"
                            ))
                            .accessTokenManager(new AndroidAccessTokenManager(context))
                            .loggingEnabled(true)
                            .build();
                }
            }
        }
        return _instance;
    }
}
