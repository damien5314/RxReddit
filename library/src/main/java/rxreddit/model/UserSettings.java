package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class UserSettings {

    @SerializedName("beta")
    Boolean beta;
    @SerializedName("clickgadget")
    Boolean clickgadget;
    @SerializedName("collapse_read_messages")
    Boolean collapseReadMessages;
    @SerializedName("compress")
    Boolean compress;
    @SerializedName("creddit_autorenew")
    Boolean credditAutoRenew;
    @SerializedName("default_comment_sort")
    String defaultCommentSort;
    @SerializedName("domain_details")
    Boolean domainDetails;
    @SerializedName("email_messages")
    Boolean emailMessages;
    @SerializedName("enable_default_themes")
    Boolean enableDefaultThemes;
    @SerializedName("hide_ads")
    Boolean hideAds;
    @SerializedName("hide_downs")
    Boolean hideDowns;
    @SerializedName("hide_from_robots")
    Boolean hideFromRobots;
    @SerializedName("hide_locationbar")
    Boolean hideLocationBar;
    @SerializedName("hide_ups")
    Boolean hideUps;
    @SerializedName("highlight_controversial")
    Boolean highlightControversial;
    @SerializedName("highlight_new_comments")
    Boolean highlightNewComments;
    @SerializedName("ignore_suggested_sort")
    Boolean ignoreSuggestedSort;
    @SerializedName("label_nsfw")
    Boolean labelNsfw;
    @SerializedName("lang")
    String lang;
    @SerializedName("mark_messages_read")
    Boolean markMessagesRead;
    @SerializedName("media")
    String media;
    @SerializedName("min_comment_score")
    Integer minCommentScore;
    @SerializedName("min_link_score")
    Integer minLinkScore;
    @SerializedName("monitor_mentions")
    Boolean monitorMentions;
    @SerializedName("newwindow")
    Boolean newWindow;
    @SerializedName("no_profanity")
    Boolean noProfanity;
    @SerializedName("num_comments")
    Integer numComments;
    @SerializedName("numsites")
    Integer numLinks;
    @SerializedName("organic")
    Boolean organic;
    @SerializedName("over_18")
    Boolean over18;
    @SerializedName("public_feeds")
    Boolean publicFeeds;
    @SerializedName("public_votes")
    Boolean publicVotes;
    @SerializedName("research")
    Boolean research;
    @SerializedName("show_flair")
    Boolean showFlair;
    @SerializedName("show_gold_expiration")
    Boolean showGoldExpiration;
    @SerializedName("show_link_flair")
    Boolean showLinkFlair;
    @SerializedName("show_promote")
    Boolean showPromote;
    @SerializedName("show_stylesheets")
    Boolean showStylesheets;
    @SerializedName("show_trending")
    Boolean showTrending;
    @SerializedName("store_visits")
    Boolean storeVisits;
    @SerializedName("theme_selector")
    String themeSelector;
    @SerializedName("threaded_messages")
    Boolean threadedMessages;
    @SerializedName("use_global_defaults")
    Boolean useGlobalDefaults;

    public boolean getBeta() {
        return beta;
    }

    public boolean getClickgadget() {
        return clickgadget;
    }

    public boolean getCollapseReadMessages() {
        return collapseReadMessages;
    }

    public boolean getCompress() {
        return compress;
    }

    public boolean getCredditAutoRenew() {
        return credditAutoRenew;
    }

    public String getDefaultCommentSort() {
        return defaultCommentSort;
    }

    public boolean getDomainDetails() {
        return domainDetails;
    }

    public boolean getEmailMessages() {
        return emailMessages;
    }

    public boolean getEnableDefaultThemes() {
        return enableDefaultThemes;
    }

    public boolean getHideAds() {
        return hideAds;
    }

    public boolean getHideDowns() {
        return hideDowns;
    }

    public boolean getHideFromRobots() {
        return hideFromRobots;
    }

    public boolean getHideLocationBar() {
        return hideLocationBar;
    }

    public boolean getHideUps() {
        return hideUps;
    }

    public boolean getHighlightControversial() {
        return highlightControversial;
    }

    public boolean getHighlightNewComments() {
        return highlightNewComments;
    }

    public boolean getIgnoreSuggestedSort() {
        return ignoreSuggestedSort;
    }

    public boolean getLabelNsfw() {
        return labelNsfw;
    }

    public String getLang() {
        return lang;
    }

    public boolean getMarkMessagesRead() {
        return markMessagesRead;
    }

    public String getMedia() {
        return media;
    }

    public Integer getMinCommentScore() {
        return minCommentScore;
    }

    public Integer getMinLinkScore() {
        return minLinkScore;
    }

    public boolean getMonitorMentions() {
        return monitorMentions;
    }

    public boolean getNewWindow() {
        return newWindow;
    }

    public boolean getNoProfanity() {
        return noProfanity;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public Integer getNumLinks() {
        return numLinks;
    }

    public boolean getOrganic() {
        return organic;
    }

    public boolean getOver18() {
        return over18;
    }

    public boolean getPublicFeeds() {
        return publicFeeds == null ? false : publicFeeds;
    }

    public boolean getPublicVotes() {
        return publicVotes;
    }

    public boolean getResearch() {
        return research;
    }

    public boolean getShowFlair() {
        return showFlair;
    }

    public boolean getShowGoldExpiration() {
        return showGoldExpiration;
    }

    public boolean getShowLinkFlair() {
        return showLinkFlair;
    }

    public boolean getShowPromote() {
        return showPromote == null ? false : showPromote;
    }

    public boolean getShowStylesheets() {
        return showStylesheets;
    }

    public boolean getShowTrending() {
        return showTrending;
    }

    public boolean getStoreVisits() {
        return storeVisits;
    }

    public String getThemeSelector() {
        return themeSelector;
    }

    public boolean getThreadedMessages() {
        return threadedMessages;
    }

    public boolean getUseGlobalDefaults() {
        return useGlobalDefaults;
    }
}
