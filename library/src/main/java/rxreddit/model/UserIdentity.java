package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class UserIdentity extends ListingData {

    @SerializedName("has_mail")
    Boolean hasMail;
    @SerializedName("name")
    String name;
    @SerializedName("created")
    Long created;
    @SerializedName("hide_from_robots")
    Boolean hideFromRobots;
    @SerializedName("gold_creddits")
    Integer goldCreddits;
    @SerializedName("created_utc")
    Long createdUTC;
    @SerializedName("has_mod_mail")
    Boolean hasModMail;
    @SerializedName("link_karma")
    Integer linkKarma;
    @SerializedName("comment_karma")
    Integer commentKarma;
    @SerializedName("over_18")
    Boolean isOver18;
    @SerializedName("is_gold")
    Boolean isGold;
    @SerializedName("is_mod")
    Boolean isMod;
    @SerializedName("gold_expiration")
    Long goldExpiration;
    @SerializedName("has_verified_email")
    Boolean hasVerifiedEmail;
    @SerializedName("inbox_count")
    Integer inboxCount;
    @SerializedName("is_friend")
    Boolean isFriend;

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
