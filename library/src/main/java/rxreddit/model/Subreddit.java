package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Subreddit extends Listing {

  Data data;

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

    @SerializedName("banner_img")
    String bannerImage;

    @SerializedName("submit_text_html")
    String submitTextHtml;

    @SerializedName("user_is_banned")
    Boolean userIsBanned;

    @SerializedName("user_is_contributor")
    Boolean userIsContributor;

    @SerializedName("submit_text")
    String submitText;

    @SerializedName("display_name")
    String displayName;

    @SerializedName("header_img")
    String headerImageUrl;

    @SerializedName("description_html")
    String descriptionHtml;

    @SerializedName("title")
    String title;

    @SerializedName("collapse_deleted_comments")
    Boolean collapseDeletedComments;

    @SerializedName("public_description")
    String publicDescription;

    @SerializedName("over18")
    Boolean over18;

    @SerializedName("public_description_html")
    String publicDescriptionHtml;

    @SerializedName("icon_size")
    List<Integer> iconSize = new ArrayList<>();

    @SerializedName("icon_img")
    String iconImg;

    @SerializedName("header_title")
    String headerTitle;

    @SerializedName("description")
    String description;

    @SerializedName("user_is_muted")
    Boolean userIsMuted;

    @SerializedName("submit_link_label")
    String submitLinkLabel;

    @SerializedName("header_size")
    Integer[] headerSize;

    @SerializedName("public_traffic")
    Boolean publicTraffic;

    @SerializedName("accounts_active")
    Integer accountsActive;

    @SerializedName("subscribers")
    Integer subscribers;

    @SerializedName("submit_text_label")
    String submitTextLabel;

    @SerializedName("lang")
    String lang;

    @SerializedName("name")
    String name;

    @SerializedName("created")
    Long created;

    @SerializedName("url")
    String url;

    @SerializedName("quarantine")
    Boolean quarantine;

    @SerializedName("hide_ads")
    Boolean hideAds;

    @SerializedName("created_utc")
    Long createdUtc;

    @SerializedName("banner_size")
    List<Integer> bannerSize = new ArrayList<>();

    @SerializedName("user_is_moderator")
    Boolean userIsModerator;

    @SerializedName("user_sr_theme_enabled")
    Boolean userSrThemeEnabled;

    @SerializedName("comment_score_hide_mins")
    Integer commentScoreHideMins;

    @SerializedName("subreddit_type")
    String subredditType;

    @SerializedName("submission_type")
    String submissionType;

    @SerializedName("user_is_subscriber")
    Boolean userIsSubscriber;
  }
}
