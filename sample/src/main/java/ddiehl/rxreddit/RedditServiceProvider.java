package ddiehl.rxreddit;

import android.content.Context;

import ddiehl.rxreddit.sample.BuildConfig;
import rxreddit.RxRedditUtil;
import rxreddit.android.AndroidAccessTokenManager;
import rxreddit.android.AndroidUtil;
import rxreddit.api.RedditService;

public class RedditServiceProvider {

  private RedditServiceProvider() { }

  private static RedditService _instance;

  public static RedditService get(Context context) {
    if (_instance == null) {
      synchronized (RedditServiceProvider.class) {
        if (_instance == null) {
          _instance = new RedditService(
              BuildConfig.REDDIT_APP_ID,
              BuildConfig.REDDIT_REDIRECT_URI,
              AndroidUtil.getDeviceId(context),
              RxRedditUtil.getUserAgent(
                  "android", "ddiehl.rxreddit.sampleapp", BuildConfig.VERSION_NAME, "damien5314"),
              new AndroidAccessTokenManager(context)
          );
        }
      }
    }
    return _instance;
  }
}
