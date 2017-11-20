package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Link extends Listing implements Votable, Savable, Hideable {

    private Data data;

    public static class Data extends ListingData {

        @SerializedName("preview") Preview preview; // New field for preview images
        @SerializedName("domain") String domain;
        @SerializedName("banned_by") String bannedBy;
        @SerializedName("media_embed") MediaEmbed mediaEmbed;
        @SerializedName("subreddit") String subreddit;
        @SerializedName("selftext_html") String selftextHtml;
        @SerializedName("selftext") String selftext;
        @SerializedName("edited") String edited;
        @SerializedName("likes") Boolean likes;
        @SerializedName("user_reports") List<UserReport> userReports;
        @SerializedName("link_flair_text") String linkFlairText;
        @SerializedName("gilded") Integer gilded;
        @SerializedName("archived") Boolean isArchived;
        @SerializedName("clicked") Boolean clicked;
        @SerializedName("author") String author;
        @SerializedName("num_comments") Integer numComments;
        @SerializedName("score") Integer score;
        @SerializedName("approved_by") String approvedBy;
        @SerializedName("over_18") Boolean over18;
        @SerializedName("hidden") Boolean hidden;
        @SerializedName("thumbnail") String thumbnail;
        @SerializedName("subreddit_id") String subredditId;
        @SerializedName("hide_score") Boolean hideScore;
        @SerializedName("link_flair_css_class") String linkFlairCssClass;
        @SerializedName("author_flair_css_class") String authorFlairCssClass;
        @SerializedName("downs") Integer downs;
        @SerializedName("saved") Boolean saved;
        @SerializedName("stickied") Boolean stickied;
        @SerializedName("is_self") Boolean isSelf;
        @SerializedName("permalink") String permalink;
        @SerializedName("created") Double created;
        @SerializedName("url") String url;
        @SerializedName("author_flair_text") String authorFlairText;
        @SerializedName("title") String title;
        @SerializedName("created_utc") Double createdUtc;
        @SerializedName("distinguished") String distinguished;
        @SerializedName("media") Media media;
        @SerializedName("mod_reports") List<ModReport> modReports;
        @SerializedName("visited") Boolean visited;
        @SerializedName("num_reports") Integer numReports;
        @SerializedName("ups") Integer ups;
    }

    public static class Preview {

        @SerializedName("images") List<Image> images;

        public List<Image> getImages() {
            return images;
        }
    }

    //region Public API

    @Override
    public String getId() {
        return data.id;
    }

    public String getDomain() {
        return data.domain;
    }

    public MediaEmbed getMediaEmbed() {
        return data.mediaEmbed;
    }

    public String getSubreddit() {
        return data.subreddit;
    }

    public String getSelftextHtml() {
        return data.selftextHtml;
    }

    public String getSelftext() {
        return data.selftext;
    }

    public String isEdited() {
        return data.edited;
    }

    @Override
    public Boolean isLiked() {
        return data.likes;
    }

    @Override
    public void isLiked(Boolean b) {
        data.likes = b;
    }

    public List<UserReport> getUserReports() {
        return data.userReports;
    }

    public Object getLinkFlairText() {
        return data.linkFlairText;
    }

    public Integer getGilded() {
        return data.gilded;
    }

    public boolean isArchived() {
        return data.isArchived == null ? false : data.isArchived;
    }

    public boolean getClicked() {
        return data.clicked == null ? false : data.clicked;
    }

    public String getAuthor() {
        return data.author;
    }

    public Integer getNumComments() {
        return data.numComments;
    }

    public Integer getScore() {
        return data.score;
    }

    public void setScore(Integer score) {
        data.score = score;
    }

    @Override
    public void applyVote(int direction) {
        int scoreDiff = direction - getLikedScore();
        switch (direction) {
            case 0:
                isLiked(null);
                break;
            case 1:
                isLiked(true);
                break;
            case -1:
                isLiked(false);
                break;
        }
        if (data.score == null) return;
        data.score += scoreDiff;
    }

    private int getLikedScore() {
        if (isLiked() == null)
            return 0;
        else if (isLiked())
            return 1;
        else
            return -1;
    }

    public Object getApprovedBy() {
        return data.approvedBy;
    }

    public boolean getOver18() {
        return data.over18 == null ? false : data.over18;
    }

    public boolean isHidden() {
        return data.hidden == null ? false : data.hidden;
    }

    public void isHidden(Boolean b) {
        data.hidden = b;
    }

    public String getThumbnail() {
        return data.thumbnail;
    }

    public String getSubredditId() {
        return data.subredditId;
    }

    public boolean isScoreHidden() {
        return data.hideScore == null ? false : data.hideScore;
    }

    public Object getLinkFlairCssClass() {
        return data.linkFlairCssClass;
    }

    public Object getAuthorFlairCssClass() {
        return data.authorFlairCssClass;
    }

    public Integer getDowns() {
        return data.downs;
    }

    public boolean isSaved() {
        return data.saved == null ? false : data.saved;
    }

    public void isSaved(boolean b) {
        data.saved = b;
    }

    public boolean getStickied() {
        return data.stickied == null ? false : data.stickied;
    }

    public boolean isSelf() {
        return data.isSelf == null ? false : data.isSelf;
    }

    public String getPermalink() {
        return data.permalink;
    }

    public Double getCreated() {
        return data.created;
    }

    public String getUrl() {
        return data.url;
    }

    public Object getAuthorFlairText() {
        return data.authorFlairText;
    }

    public String getTitle() {
        return data.title;
    }

    public Double getCreatedUtc() {
        return data.createdUtc;
    }

    public String getDistinguished() {
        return data.distinguished;
    }

    public Media getMedia() {
        return data.media;
    }

    public List<ModReport> getModReports() {
        return data.modReports;
    }

    public boolean getVisited() {
        return data.visited == null ? false : data.visited;
    }

    public Object getNumReports() {
        return data.numReports;
    }

    public Integer getUps() {
        return data.ups;
    }

    public List<Image> getPreviewImages() {
        if (data.preview == null)
            return null;
        return data.preview.images;
    }

    //endregion
}
