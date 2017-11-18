package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Link extends Listing implements Votable, Savable, Hideable {

    private Data data;

    public static class Data extends ListingData {

        private Preview preview; // New field for preview images
        private String domain;
        @SerializedName("banned_by")
        private String bannedBy;
        @SerializedName("media_embed")
        private MediaEmbed mediaEmbed;
        private String subreddit;
        @SerializedName("selftext_html")
        private String selftextHtml;
        private String selftext;
        private String edited;
        private Boolean likes;
        @SerializedName("user_reports")
        private List<UserReport> userReports;
        @SerializedName("link_flair_text")
        private String linkFlairText;
        private Integer gilded;
        @SerializedName("archived")
        private Boolean isArchived;
        private Boolean clicked;
        private String author;
        @SerializedName("num_comments")
        private Integer numComments;
        private Integer score;
        @SerializedName("approved_by")
        private String approvedBy;
        @SerializedName("over_18")
        private Boolean over18;
        private Boolean hidden;
        private String thumbnail;
        @SerializedName("subreddit_id")
        private String subredditId;
        @SerializedName("hide_score")
        private Boolean hideScore;
        @SerializedName("link_flair_css_class")
        private String linkFlairCssClass;
        @SerializedName("author_flair_css_class")
        private String authorFlairCssClass;
        private Integer downs;
        private Boolean saved;
        private Boolean stickied;
        @SerializedName("is_self")
        private Boolean isSelf;
        private String permalink;
        private Double created;
        private String url;
        @SerializedName("author_flair_text")
        private String authorFlairText;
        private String title;
        @SerializedName("created_utc")
        private Double createdUtc;
        private String distinguished;
        private Media media;
        @SerializedName("mod_reports")
        private List<ModReport> modReports;
        private Boolean visited;
        @SerializedName("num_reports")
        private Integer numReports;
        private Integer ups;
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
