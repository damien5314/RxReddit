package rxreddit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserIdentity extends ListingData {
  @Expose
  @SerializedName("has_mail")
  private Boolean hasMail;
  @Expose
  private String name;
  @Expose
  private Long created;
  @Expose @SerializedName("hide_from_robots")
  private Boolean hideFromRobots;
  @Expose @SerializedName("gold_creddits")
  private Integer goldCreddits;
  @Expose @SerializedName("created_utc")
  private Long createdUTC;
  @Expose @SerializedName("has_mod_mail")
  private Boolean hasModMail;
  @Expose @SerializedName("link_karma")
  private Integer linkKarma;
  @Expose @SerializedName("comment_karma")
  private Integer commentKarma;
  @Expose @SerializedName("over_18")
  private Boolean isOver18;
  @Expose @SerializedName("is_gold")
  private Boolean isGold;
  @Expose @SerializedName("is_mod")
  private Boolean isMod;
  @Expose @SerializedName("gold_expiration")
  private Long goldExpiration;
  @Expose @SerializedName("has_verified_email")
  private Boolean hasVerifiedEmail;
  @Expose @SerializedName("inbox_count")
  private Integer inboxCount;
  @Expose @SerializedName("is_friend")
  private Boolean isFriend;

  public Boolean hasMail() {
    return this.hasMail != null && this.hasMail;
  }

  public void hasMail(Boolean hasMail) {
    this.hasMail = hasMail;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getCreated() {
    return this.created;
  }

  public void setCreated(Long created) {
    this.created = created;
  }

  public Boolean isHiddenFromRobots() {
    return this.hideFromRobots != null && this.hideFromRobots;
  }

  public void isHiddenFromRobots(Boolean hideFromRobots) {
    this.hideFromRobots = hideFromRobots;
  }

  public Integer getGoldCreddits() {
    return this.goldCreddits;
  }

  public void setGoldCreddits(Integer goldCreddits) {
    this.goldCreddits = goldCreddits;
  }

  public Long getCreatedUTC() {
    return this.createdUTC;
  }

  public void setCreatedUTC(Long createdUTC) {
    this.createdUTC = createdUTC;
  }

  public Boolean hasModMail() {
    return this.hasModMail != null && this.hasModMail;
  }

  public void hasModMail(Boolean hasModMail) {
    this.hasModMail = hasModMail;
  }

  public Integer getLinkKarma() {
    return this.linkKarma;
  }

  public void setLinkKarma(Integer linkKarma) {
    this.linkKarma = linkKarma;
  }

  public Integer getCommentKarma() {
    return this.commentKarma;
  }

  public void setCommentKarma(Integer commentKarma) {
    this.commentKarma = commentKarma;
  }

  public Boolean isOver18() {
    return this.isOver18 != null && this.isOver18;
  }

  public void isOver18(Boolean isOver18) {
    this.isOver18 = isOver18;
  }

  public Boolean isGold() {
    return this.isGold != null && this.isGold;
  }

  public void isGold(Boolean isGold) {
    this.isGold = isGold;
  }

  public Boolean isMod() {
    return this.isMod != null && this.isMod;
  }

  public void isMod(Boolean isMod) {
    this.isMod = isMod;
  }

  public Long getGoldExpiration() {
    return this.goldExpiration;
  }

  public void setGoldExpiration(Long goldExpiration) {
    this.goldExpiration = goldExpiration;
  }

  public Boolean hasVerifiedEmail() {
    return this.hasVerifiedEmail != null && this.hasVerifiedEmail;
  }

  public void hasVerifiedEmail(Boolean hasVerifiedEmail) {
    this.hasVerifiedEmail = hasVerifiedEmail;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getInboxCount() {
    return this.inboxCount;
  }

  public void setInboxCount(Integer inboxCount) {
    this.inboxCount = inboxCount;
  }

  public boolean isFriend() {
    return this.isFriend != null && this.isFriend;
  }

  public void isFriend(boolean b) {
    isFriend = b;
  }
}