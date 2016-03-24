package rxreddit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSettings {
  @Expose @SerializedName("beta") Boolean mBeta;
  @Expose @SerializedName("clickgadget") Boolean mClickgadget;
  @Expose @SerializedName("collapse_read_messages") Boolean mCollapseReadMessages;
  @Expose @SerializedName("compress") Boolean mCompress;
  @Expose @SerializedName("creddit_autorenew") Boolean mCredditAutoRenew;
  @Expose @SerializedName("default_comment_sort") String mDefaultCommentSort;
  @Expose @SerializedName("domain_details") Boolean mDomainDetails;
  @Expose @SerializedName("email_messages") Boolean mEmailMessages;
  @Expose @SerializedName("enable_default_themes") Boolean mEnableDefaultThemes;
  @Expose @SerializedName("hide_ads") Boolean mHideAds;
  @Expose @SerializedName("hide_downs") Boolean mHideDowns;
  @Expose @SerializedName("hide_from_robots") Boolean mHideFromRobots;
  @Expose @SerializedName("hide_locationbar") Boolean mHideLocationBar;
  @Expose @SerializedName("hide_ups") Boolean mHideUps;
  @Expose @SerializedName("highlight_controversial") Boolean mHighlightControversial;
  @Expose @SerializedName("highlight_new_comments") Boolean mHighlightNewComments;
  @Expose @SerializedName("ignore_suggested_sort") Boolean mIgnoreSuggestedSort;
  @Expose @SerializedName("label_nsfw") Boolean mLabelNsfw;
  @Expose @SerializedName("lang") String mLang;
  @Expose @SerializedName("mark_messages_read") Boolean mMarkMessagesRead;
  @Expose @SerializedName("media") String mMedia;
  @Expose @SerializedName("min_comment_score") Integer mMinCommentScore;
  @Expose @SerializedName("min_link_score") Integer mMinLinkScore;
  @Expose @SerializedName("monitor_mentions") Boolean mMonitorMentions;
  @Expose @SerializedName("newwindow") Boolean mNewWindow;
  @Expose @SerializedName("no_profanity") Boolean mNoProfanity;
  @Expose @SerializedName("num_comments") Integer mNumComments;
  @Expose @SerializedName("numsites") Integer mNumLinks;
  @Expose @SerializedName("organic") Boolean mOrganic;
  @Expose @SerializedName("over_18") Boolean mOver18;
  @Expose @SerializedName("public_feeds") Boolean mPublicFeeds;
  @Expose @SerializedName("public_votes") Boolean mPublicVotes;
  @Expose @SerializedName("research") Boolean mResearch;
  @Expose @SerializedName("show_flair") Boolean mShowFlair;
  @Expose @SerializedName("show_gold_expiration") Boolean mShowGoldExpiration;
  @Expose @SerializedName("show_link_flair") Boolean mShowLinkFlair;
  @Expose @SerializedName("show_promote") Boolean mShowPromote;
  @Expose @SerializedName("show_stylesheets") Boolean mShowStylesheets;
  @Expose @SerializedName("show_trending") Boolean mShowTrending;
  @Expose @SerializedName("store_visits") Boolean mStoreVisits;
  @Expose @SerializedName("theme_selector") String mThemeSelector;
  @Expose @SerializedName("threaded_messages") Boolean mThreadedMessages;
  @Expose @SerializedName("use_global_defaults") Boolean mUseGlobalDefaults;

  public boolean getBeta() {
    return mBeta;
  }

  public boolean getClickgadget() {
    return mClickgadget;
  }

  public boolean getCollapseReadMessages() {
    return mCollapseReadMessages;
  }

  public boolean getCompress() {
    return mCompress;
  }

  public boolean getCredditAutoRenew() {
    return mCredditAutoRenew;
  }

  public String getDefaultCommentSort() {
    return mDefaultCommentSort;
  }

  public boolean getDomainDetails() {
    return mDomainDetails;
  }

  public boolean getEmailMessages() {
    return mEmailMessages;
  }

  public boolean getEnableDefaultThemes() {
    return mEnableDefaultThemes;
  }

  public boolean getHideAds() {
    return mHideAds;
  }

  public boolean getHideDowns() {
    return mHideDowns;
  }

  public boolean getHideFromRobots() {
    return mHideFromRobots;
  }

  public boolean getHideLocationBar() {
    return mHideLocationBar;
  }

  public boolean getHideUps() {
    return mHideUps;
  }

  public boolean getHighlightControversial() {
    return mHighlightControversial;
  }

  public boolean getHighlightNewComments() {
    return mHighlightNewComments;
  }

  public boolean getIgnoreSuggestedSort() {
    return mIgnoreSuggestedSort;
  }

  public boolean getLabelNsfw() {
    return mLabelNsfw;
  }

  public String getLang() {
    return mLang;
  }

  public boolean getMarkMessagesRead() {
    return mMarkMessagesRead;
  }

  public String getMedia() {
    return mMedia;
  }

  public Integer getMinCommentScore() {
    return mMinCommentScore;
  }

  public Integer getMinLinkScore() {
    return mMinLinkScore;
  }

  public boolean getMonitorMentions() {
    return mMonitorMentions;
  }

  public boolean getNewWindow() {
    return mNewWindow;
  }

  public boolean getNoProfanity() {
    return mNoProfanity;
  }

  public Integer getNumComments() {
    return mNumComments;
  }

  public Integer getNumLinks() {
    return mNumLinks;
  }

  public boolean getOrganic() {
    return mOrganic;
  }

  public boolean getOver18() {
    return mOver18;
  }

  public boolean getPublicFeeds() {
    return mPublicFeeds == null ? false : mPublicFeeds;
  }

  public boolean getPublicVotes() {
    return mPublicVotes;
  }

  public boolean getResearch() {
    return mResearch;
  }

  public boolean getShowFlair() {
    return mShowFlair;
  }

  public boolean getShowGoldExpiration() {
    return mShowGoldExpiration;
  }

  public boolean getShowLinkFlair() {
    return mShowLinkFlair;
  }

  public boolean getShowPromote() {
    return mShowPromote == null ? false : mShowPromote;
  }

  public boolean getShowStylesheets() {
    return mShowStylesheets;
  }

  public boolean getShowTrending() {
    return mShowTrending;
  }

  public boolean getStoreVisits() {
    return mStoreVisits;
  }

  public String getThemeSelector() {
    return mThemeSelector;
  }

  public boolean getThreadedMessages() {
    return mThreadedMessages;
  }

  public boolean getUseGlobalDefaults() {
    return mUseGlobalDefaults;
  }
}
