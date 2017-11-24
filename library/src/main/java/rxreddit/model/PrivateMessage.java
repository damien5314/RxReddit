package rxreddit.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PrivateMessage extends Listing {

    @SerializedName("data") Data data;

    @Override
    public String getId() {
        return data.id;
    }

    public String getBody() {
        return data.body;
    }

    public boolean wasComment() {
        return data.wasComment == null ? false : data.wasComment;
    }

    public String getFirstMessage() {
        return data.firstMessage;
    }

    public String getFullname() {
        return data.fullname;
    }

    public String getFirstMessageName() {
        return data.firstMessageName;
    }

    public Long getCreated() {
        return data.created;
    }

    public String getDestination() {
        return data.dest;
    }

    public String getAuthor() {
        return data.author;
    }

    public Long getCreatedUtc() {
        return data.createdUtc;
    }

    public String getBodyHtml() {
        return data.bodyHtml;
    }

    public String getSubreddit() {
        return data.subreddit;
    }

    public String getParentId() {
        return data.parentId;
    }

    public String getContext() {
        return data.context;
    }

    public ListingResponse getReplies() {
        return data.replies;
    }

    public Boolean isUnread() {
        return data.isNew;
    }

    public void markUnread(boolean b) {
        data.isNew = b;
    }

    public String getDistinguished() {
        return data.distinguished;
    }

    public String getSubject() {
        return data.subject;
    }

    public static class Data extends ListingData {

        @SerializedName("body") String body;
        @SerializedName("was_comment") Boolean wasComment;
        @SerializedName("first_message") String firstMessage;
        @SerializedName("name") String fullname;
        @SerializedName("first_message_name") String firstMessageName;
        @SerializedName("created") Long created;
        @SerializedName("dest") String dest;
        @SerializedName("author") String author;
        @SerializedName("created_utc") Long createdUtc;
        @SerializedName("body_html") String bodyHtml;
        @SerializedName("subreddit") String subreddit;
        @SerializedName("parent_id") String parentId;
        @SerializedName("context") String context;
        @SerializedName("replies") ListingResponse replies;
        @SerializedName("new") Boolean isNew;
        @SerializedName("distinguished") String distinguished;
        @SerializedName("subject") String subject;
    }
}
