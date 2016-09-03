package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class UserSettings {

  @SerializedName("beta")
  Boolean mBeta;
  @SerializedName("clickgadget")
  Boolean mClickgadget;
  @SerializedName("collapse_read_messages")
  Boolean mCollapseReadMessages;
  @SerializedName("compress")
  Boolean mCompress;
  @SerializedName("creddit_autorenew")
  Boolean mCredditAutoRenew;
  @SerializedName("default_comment_sort")
  String mDefaultCommentSort;
  @SerializedName("domain_details")
  Boolean mDomainDetails;
  @SerializedName("email_messages")
  Boolean mEmailMessages;
  @SerializedName("enable_default_themes")
  Boolean mEnableDefaultThemes;
  @SerializedName("hide_ads")
  Boolean mHideAds;
  @SerializedName("hide_downs")
  Boolean mHideDowns;
  @SerializedName("hide_from_robots")
  Boolean mHideFromRobots;
  @SerializedName("hide_locationbar")
  Boolean mHideLocationBar;
  @SerializedName("hide_ups")
  Boolean mHideUps;
  @SerializedName("highlight_controversial")
  Boolean mHighlightControversial;
  @SerializedName("highlight_new_comments")
  Boolean mHighlightNewComments;
  @SerializedName("ignore_suggested_sort")
  Boolean mIgnoreSuggestedSort;
  @SerializedName("label_nsfw")
  Boolean mLabelNsfw;
  @SerializedName("lang")
  String mLang;
  @SerializedName("mark_messages_read")
  Boolean mMarkMessagesRead;
  @SerializedName("media")
  String mMedia;
  @SerializedName("min_comment_score")
  Integer mMinCommentScore;
  @SerializedName("min_link_score")
  Integer mMinLinkScore;
  @SerializedName("monitor_mentions")
  Boolean mMonitorMentions;
  @SerializedName("newwindow")
  Boolean mNewWindow;
  @SerializedName("no_profanity")
  Boolean mNoProfanity;
  @SerializedName("num_comments")
  Integer mNumComments;
  @SerializedName("numsites")
  Integer mNumLinks;
  @SerializedName("organic")
  Boolean mOrganic;
  @SerializedName("over_18")
  Boolean mOver18;
  @SerializedName("public_feeds")
  Boolean mPublicFeeds;
  @SerializedName("public_votes")
  Boolean mPublicVotes;
  @SerializedName("research")
  Boolean mResearch;
  @SerializedName("show_flair")
  Boolean mShowFlair;
  @SerializedName("show_gold_expiration")
  Boolean mShowGoldExpiration;
  @SerializedName("show_link_flair")
  Boolean mShowLinkFlair;
  @SerializedName("show_promote")
  Boolean mShowPromote;
  @SerializedName("show_stylesheets")
  Boolean mShowStylesheets;
  @SerializedName("show_trending")
  Boolean mShowTrending;
  @SerializedName("store_visits")
  Boolean mStoreVisits;
  @SerializedName("theme_selector")
  String mThemeSelector;
  @SerializedName("threaded_messages")
  Boolean mThreadedMessages;
  @SerializedName("use_global_defaults")
  Boolean mUseGlobalDefaults;

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
