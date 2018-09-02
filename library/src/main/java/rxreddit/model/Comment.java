package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Comment extends AbsComment implements Votable, Savable {

    @SerializedName("data")
    Data data;

    private boolean isCollapsed = false;

    @Override
    public String getId() {
        return data.id;
    }

    public String getUrl() {
        return String.format(
                "http://www.reddit.com/r/%s/comments/%s?comment=%s",
                getSubreddit(),
                getLinkId(), // Remove the type prefix (t3_, etc)
                getId()
        );
    }

    @Override
    public boolean isCollapsed() {
        return this.isCollapsed;
    }

    @Override
    public void setCollapsed(boolean b) {
        this.isCollapsed = b;
    }

    public String getSubredditId() {
        return data.subredditId;
    }

    public Object getBannedBy() {
        return data.bannedBy;
    }

    public String getLinkId() {
        // This is a hack to get the linkId from the context
        // because comments in the inbox do not have the linkId for some reason
        String linkId = data.linkId;
        if (linkId == null) {
            String linkUrl = data.context;
            int i = linkUrl.indexOf("/comments/") + 10; // Add length of string
            int j = linkUrl.indexOf("/", i + 1);
            data.linkId = linkUrl.substring(i, j);
        }
        int i = data.linkId.indexOf('_');
        if (i == -1) {
            return data.linkId;
        } else {
            return data.linkId.substring(i + 1);
        }
    }

    @Override
    public Boolean isLiked() {
        return data.isLiked;
    }

    @Override
    public void isLiked(Boolean b) {
        data.isLiked = b;
    }

    public ListingResponse getReplies() {
        return data.replies;
    }

    public void setReplies(ListingResponse response) {
        data.replies = response;
    }

    public List<Object> getUserReports() {
        return data.userReports;
    }

    @Override
    public boolean isSaved() {
        return data.saved == null ? false : data.saved;
    }

    @Override
    public void isSaved(boolean b) {
        data.saved = b;
    }

    public Integer getGilded() {
        return data.gilded;
    }

    @Override
    public boolean isArchived() {
        return data.isArchived == null ? false : data.isArchived;
    }

    public Object getReportReasons() {
        return data.reportReasons;
    }

    public String getAuthor() {
        return data.author;
    }

    public void setAuthor(String author) {
        data.author = author;
    }

    public String getAuthorFlairCssClass() {
        return data.authorFlairCssClass;
    }

    public String getAuthorFlairText() {
        return data.authorFlairText;
    }

    public Integer getScore() {
        return isScoreHidden() ? null : data.score;
    }

    public void setScore(Integer score) {
        data.score = score;
    }

    public Object getApprovedBy() {
        return data.approvedBy;
    }

    public int getControversiality() {
        return data.controversiality;
    }

    public String getBody() {
        return data.body;
    }

    public String isEdited() {
        return data.edited;
    }

    public int getDowns() {
        return data.downs;
    }

    public String getBodyHtml() {
        return data.bodyHtml;
    }

    public String getSubreddit() {
        return data.subreddit;
    }

    public boolean isScoreHidden() {
        return data.hideScore == null ? false : data.hideScore;
    }

    public double getCreated() {
        return data.created;
    }

    public Double getCreateUtc() {
        return data.createUtc;
    }

    public int getUps() {
        return data.ups;
    }

    public List<Object> getModReports() {
        return data.modReports;
    }

    public Object getNumReports() {
        return data.numReports;
    }

    public String getDistinguished() {
        return data.distinguished;
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

    public String getLinkTitle() {
        return data.linkTitle;
    }

    public String getRemovalReason() {
        return data.removalReason;
    }

    public String getLinkAuthor() {
        return data.linkAuthor;
    }

    @Override
    public String getParentId() {
        if (data.parentId == null) return null;
        if (data.parentId.contains("_")) {
            return data.parentId.substring(3);
        } else return data.parentId;
    }

    public String getLinkUrl() {
        return data.linkUrl;
    }

    public String getSubject() {
        return data.subject;
    }

    public String getContext() {
        return data.context;
    }

    @Override
    public String toString() {
        return String.format("Comment: %s, depth %s", getAuthor(), getDepth());
    }

    public static class Data extends AbsComment.Data {

        // Attributes specific to listing views
        @SerializedName("link_title")
        String linkTitle;
        @SerializedName("removal_reason")
        String removalReason;
        @SerializedName("link_author")
        String linkAuthor;
        @SerializedName("link_url")
        String linkUrl;

        // Attributes common to all comment views
        @SerializedName("replies")
        ListingResponse replies;
        @SerializedName("subreddit_id")
        String subredditId;
        @SerializedName("banned_by")
        Object bannedBy;
        @SerializedName("link_id")
        String linkId;
        @SerializedName("likes")
        Boolean isLiked;
        @SerializedName("user_reports")
        List<Object> userReports;
        @SerializedName("saved")
        Boolean saved;
        @SerializedName("gilded")
        Integer gilded;
        @SerializedName("archived")
        Boolean isArchived;
        @SerializedName("report_reasons")
        Object reportReasons;
        @SerializedName("author")
        String author;
        @SerializedName("score")
        Integer score;
        @SerializedName("approved_by")
        Object approvedBy;
        @SerializedName("controversiality")
        int controversiality;
        @SerializedName("body")
        String body;
        @SerializedName("edited")
        String edited;
        @SerializedName("author_flair_css_class")
        String authorFlairCssClass;
        @SerializedName("downs")
        int downs;
        @SerializedName("body_html")
        String bodyHtml;
        @SerializedName("subreddit")
        String subreddit;
        @SerializedName("score_hidden")
        Boolean hideScore;
        @SerializedName("created")
        double created;
        @SerializedName("author_flair_text")
        String authorFlairText;
        @SerializedName("created_utc")
        double createUtc;
        @SerializedName("ups")
        int ups;
        @SerializedName("mod_reports")
        List<Object> modReports;
        @SerializedName("num_reports")
        Object numReports;
        @SerializedName("distinguished")
        String distinguished;
        @SerializedName("subject")
        String subject;
        @SerializedName("context")
        String context;
    }
}
