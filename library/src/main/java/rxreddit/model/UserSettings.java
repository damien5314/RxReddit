package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

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

    @Nullable
    public Boolean getBeta() {
        return beta;
    }

    @Nullable
    public Boolean getClickgadget() {
        return clickgadget;
    }

    @Nullable
    public Boolean getCollapseReadMessages() {
        return collapseReadMessages;
    }

    @Nullable
    public Boolean getCompress() {
        return compress;
    }

    @Nullable
    public Boolean getCredditAutoRenew() {
        return credditAutoRenew;
    }

    @Nullable
    public String getDefaultCommentSort() {
        return defaultCommentSort;
    }

    @Nullable
    public Boolean getDomainDetails() {
        return domainDetails;
    }

    @Nullable
    public Boolean getEmailMessages() {
        return emailMessages;
    }

    @Nullable
    public Boolean getEnableDefaultThemes() {
        return enableDefaultThemes;
    }

    @Nullable
    public Boolean getHideAds() {
        return hideAds;
    }

    @Nullable
    public Boolean getHideDowns() {
        return hideDowns;
    }

    @Nullable
    public Boolean getHideFromRobots() {
        return hideFromRobots;
    }

    @Nullable
    public Boolean getHideLocationBar() {
        return hideLocationBar;
    }

    @Nullable
    public Boolean getHideUps() {
        return hideUps;
    }

    @Nullable
    public Boolean getHighlightControversial() {
        return highlightControversial;
    }

    @Nullable
    public Boolean getHighlightNewComments() {
        return highlightNewComments;
    }

    @Nullable
    public Boolean getIgnoreSuggestedSort() {
        return ignoreSuggestedSort;
    }

    @Nullable
    public Boolean getLabelNsfw() {
        return labelNsfw;
    }

    @Nullable
    public String getLang() {
        return lang;
    }

    @Nullable
    public Boolean getMarkMessagesRead() {
        return markMessagesRead;
    }

    @Nullable
    public String getMedia() {
        return media;
    }

    @Nullable
    public Integer getMinCommentScore() {
        return minCommentScore;
    }

    @Nullable
    public Integer getMinLinkScore() {
        return minLinkScore;
    }

    @Nullable
    public Boolean getMonitorMentions() {
        return monitorMentions;
    }

    @Nullable
    public Boolean getNewWindow() {
        return newWindow;
    }

    @Nullable
    public Boolean getNoProfanity() {
        return noProfanity;
    }

    @Nullable
    public Integer getNumComments() {
        return numComments;
    }

    @Nullable
    public Integer getNumLinks() {
        return numLinks;
    }

    @Nullable
    public Boolean getOrganic() {
        return organic;
    }

    @Nullable
    public Boolean getOver18() {
        return over18;
    }

    @Nullable
    public Boolean getPublicFeeds() {
        return publicFeeds;
    }

    @Nullable
    public Boolean getPublicVotes() {
        return publicVotes;
    }

    @Nullable
    public Boolean getResearch() {
        return research;
    }

    @Nullable
    public Boolean getShowFlair() {
        return showFlair;
    }

    @Nullable
    public Boolean getShowGoldExpiration() {
        return showGoldExpiration;
    }

    @Nullable
    public Boolean getShowLinkFlair() {
        return showLinkFlair;
    }

    @Nullable
    public Boolean getShowPromote() {
        return showPromote;
    }

    @Nullable
    public Boolean getShowStylesheets() {
        return showStylesheets;
    }

    @Nullable
    public Boolean getShowTrending() {
        return showTrending;
    }

    @Nullable
    public Boolean getStoreVisits() {
        return storeVisits;
    }

    @Nullable
    public String getThemeSelector() {
        return themeSelector;
    }

    @Nullable
    public Boolean getThreadedMessages() {
        return threadedMessages;
    }

    @Nullable
    public Boolean getUseGlobalDefaults() {
        return useGlobalDefaults;
    }
}
