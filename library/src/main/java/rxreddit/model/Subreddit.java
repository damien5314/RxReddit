package rxreddit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Subreddit extends Listing {
  @Expose Data data;

  @Override
  public String getId() {
    return data.id;
  }

  public String getName() {
    return data.name;
  }

  public String getBannerImageUrl() {
    return data.bannerImage;
  }

  public void setBannerImage(String bannerImage) {
    data.bannerImage = bannerImage;
  }

  public String getSubmitTextHtml() {
    return data.submitTextHtml;
  }

  public void setSubmitTextHtml(String submitTextHtml) {
    data.submitTextHtml = submitTextHtml;
  }

  public Boolean getUserIsBanned() {
    return data.userIsBanned;
  }

  public void setUserIsBanned(Boolean userIsBanned) {
    data.userIsBanned = userIsBanned;
  }

  public Boolean getUserIsContributor() {
    return data.userIsContributor;
  }

  public void setUserIsContributor(Boolean userIsContributor) {
    data.userIsContributor = userIsContributor;
  }

  public String getSubmitText() {
    return data.submitText;
  }

  public void setSubmitText(String submitText) {
    data.submitText = submitText;
  }

  public String getDisplayName() {
    return data.displayName;
  }

  public void setDisplayName(String displayName) {
    data.displayName = displayName;
  }

  public String getHeaderImageUrl() {
    return data.headerImageUrl;
  }

  public void setHeaderImage(String headerImage) {
    data.headerImageUrl = headerImage;
  }

  public String getDescriptionHtml() {
    return data.descriptionHtml;
  }

  public void setDescriptionHtml(String descriptionHtml) {
    data.descriptionHtml = descriptionHtml;
  }

  public String getTitle() {
    return data.title;
  }

  public void setTitle(String title) {
    data.title = title;
  }

  public Boolean getCollapseDeletedComments() {
    return data.collapseDeletedComments;
  }

  public void setCollapseDeletedComments(Boolean collapseDeletedComments) {
    data.collapseDeletedComments = collapseDeletedComments;
  }

  public String getPublicDescription() {
    return data.publicDescription;
  }

  public void setPublicDescription(String publicDescription) {
    data.publicDescription = publicDescription;
  }

  public boolean isOver18() {
    return data.over18 == null ? false : data.over18;
  }

  public void setOver18(Boolean over18) {
    data.over18 = over18;
  }

  public String getPublicDescriptionHtml() {
    return data.publicDescriptionHtml;
  }

  public void setPublicDescriptionHtml(String publicDescriptionHtml) {
    data.publicDescriptionHtml = publicDescriptionHtml;
  }

  public int getIconWidth() {
    return data.iconSize.get(0);
  }

  public int getIconHeight() {
    return data.iconSize.get(1);
  }

  public void setIconWidth(int i) {
    data.iconSize.set(0, i);
  }

  public void setIconHeight(int i) {
    data.iconSize.set(1, i);
  }

  public String getIconImg() {
    return data.iconImg;
  }

  public void setIconImg(String iconImg) {
    data.iconImg = iconImg;
  }

  public String getHeaderTitle() {
    return data.headerTitle;
  }

  public void setHeaderTitle(String headerTitle) {
    data.headerTitle = headerTitle;
  }

  public String getDescription() {
    return data.description;
  }

  public void setDescription(String description) {
    data.description = description;
  }

  public Boolean getUserIsMuted() {
    return data.userIsMuted;
  }

  public void setUserIsMuted(Boolean userIsMuted) {
    data.userIsMuted = userIsMuted;
  }

  public String getSubmitLinkLabel() {
    return data.submitLinkLabel;
  }

  public void setSubmitLinkLabel(String submitLinkLabel) {
    data.submitLinkLabel = submitLinkLabel;
  }

  public Integer[] getHeaderSize() {
    return data.headerSize;
  }

  public void setHeaderSize(Integer[] headerSize) {
    data.headerSize = headerSize;
  }

  public Boolean getPublicTraffic() {
    return data.publicTraffic;
  }

  public void setPublicTraffic(Boolean publicTraffic) {
    data.publicTraffic = publicTraffic;
  }

  public Integer getAccountsActive() {
    return data.accountsActive;
  }

  public void setAccountsActive(Integer accountsActive) {
    data.accountsActive = accountsActive;
  }

  public Integer getSubscribers() {
    return data.subscribers;
  }

  public void setSubscribers(Integer subscribers) {
    data.subscribers = subscribers;
  }

  public String getSubmitTextLabel() {
    return data.submitTextLabel;
  }

  public void setSubmitTextLabel(String submitTextLabel) {
    data.submitTextLabel = submitTextLabel;
  }

  public String getLang() {
    return data.lang;
  }

  public void setLang(String lang) {
    data.lang = lang;
  }

  public void setName(String name) {
    data.name = name;
  }

  public Long getCreated() {
    return data.created;
  }

  public void setCreated(Long created) {
    data.created = created;
  }

  public String getUrl() {
    return data.url;
  }

  public void setUrl(String url) {
    data.url = url;
  }

  public Boolean getQuarantine() {
    return data.quarantine;
  }

  public void setQuarantine(Boolean quarantine) {
    data.quarantine = quarantine;
  }

  public Boolean getHideAds() {
    return data.hideAds;
  }

  public void setHideAds(Boolean hideAds) {
    data.hideAds = hideAds;
  }

  public Long getCreatedUtc() {
    return data.createdUtc;
  }

  public void setCreatedUtc(Long createdUtc) {
    data.createdUtc = createdUtc;
  }

  public int getBannerWidth() {
    return data.bannerSize.get(0);
  }

  public int getBannerHeight() {
    return data.bannerSize.get(1);
  }

  public void setBannerWidth(int i) {
    data.bannerSize.set(0, i);
  }

  public void setBannerHeight(int i) {
    data.bannerSize.set(1, i);
  }

  public Boolean getUserIsModerator() {
    return data.userIsModerator;
  }

  public void setUserIsModerator(Boolean userIsModerator) {
    data.userIsModerator = userIsModerator;
  }

  public Boolean getUserSrThemeEnabled() {
    return data.userSrThemeEnabled;
  }

  public void setUserSrThemeEnabled(Boolean userSrThemeEnabled) {
    data.userSrThemeEnabled = userSrThemeEnabled;
  }

  public Integer getCommentScoreHideMins() {
    return data.commentScoreHideMins;
  }

  public void setCommentScoreHideMins(Integer commentScoreHideMins) {
    data.commentScoreHideMins = commentScoreHideMins;
  }

  public String getSubredditType() {
    return data.subredditType;
  }

  public void setSubredditType(String subredditType) {
    data.subredditType = subredditType;
  }

  public String getSubmissionType() {
    return data.submissionType;
  }

  public void setSubmissionType(String submissionType) {
    data.submissionType = submissionType;
  }

  public Boolean getUserIsSubscriber() {
    return data.userIsSubscriber;
  }

  public void setUserIsSubscriber(Boolean userIsSubscriber) {
    data.userIsSubscriber = userIsSubscriber;
  }

  public static class Data extends ListingData {

    @Expose @SerializedName("banner_img")
    String bannerImage;

    @Expose @SerializedName("submit_text_html")
    String submitTextHtml;

    @Expose @SerializedName("user_is_banned")
    Boolean userIsBanned;

    @Expose @SerializedName("user_is_contributor")
    Boolean userIsContributor;

    @Expose @SerializedName("submit_text")
    String submitText;

    @Expose @SerializedName("display_name")
    String displayName;

    @Expose @SerializedName("header_img")
    String headerImageUrl;

    @Expose @SerializedName("description_html")
    String descriptionHtml;

    @Expose @SerializedName("title")
    String title;

    @Expose @SerializedName("collapse_deleted_comments")
    Boolean collapseDeletedComments;

    @Expose @SerializedName("public_description")
    String publicDescription;

    @Expose @SerializedName("over18")
    Boolean over18;

    @Expose @SerializedName("public_description_html")
    String publicDescriptionHtml;

    @Expose @SerializedName("icon_size")
    List<Integer> iconSize = new ArrayList<>();

    @Expose @SerializedName("icon_img")
    String iconImg;

    @Expose @SerializedName("header_title")
    String headerTitle;

    @Expose @SerializedName("description")
    String description;

    @Expose @SerializedName("user_is_muted")
    Boolean userIsMuted;

    @Expose @SerializedName("submit_link_label")
    String submitLinkLabel;

    @Expose @SerializedName("header_size")
    Integer[] headerSize;

    @Expose @SerializedName("public_traffic")
    Boolean publicTraffic;

    @Expose @SerializedName("accounts_active")
    Integer accountsActive;

    @Expose @SerializedName("subscribers")
    Integer subscribers;

    @Expose @SerializedName("submit_text_label")
    String submitTextLabel;

    @Expose @SerializedName("lang")
    String lang;

    @Expose @SerializedName("name")
    String name;

    @Expose @SerializedName("created")
    Long created;

    @Expose @SerializedName("url")
    String url;

    @Expose @SerializedName("quarantine")
    Boolean quarantine;

    @Expose @SerializedName("hide_ads")
    Boolean hideAds;

    @Expose @SerializedName("created_utc")
    Long createdUtc;

    @Expose @SerializedName("banner_size")
    List<Integer> bannerSize = new ArrayList<>();

    @Expose @SerializedName("user_is_moderator")
    Boolean userIsModerator;

    @Expose @SerializedName("user_sr_theme_enabled")
    Boolean userSrThemeEnabled;

    @Expose @SerializedName("comment_score_hide_mins")
    Integer commentScoreHideMins;

    @Expose @SerializedName("subreddit_type")
    String subredditType;

    @Expose @SerializedName("submission_type")
    String submissionType;

    @Expose @SerializedName("user_is_subscriber")
    Boolean userIsSubscriber;
  }
}
