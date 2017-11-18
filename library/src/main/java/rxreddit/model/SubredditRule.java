package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class SubredditRule {

    @SerializedName("kind")
    String kind;

    @SerializedName("description")
    String description;

    @SerializedName("short_name")
    String shortName;

    @SerializedName("created_utc")
    Long createdUtc;

    @SerializedName("priority")
    Integer priority;

    @SerializedName("description_html")
    String descriptionHtml;

    public String getKind() {
        return kind;
    }

    public String getDescription() {
        return description;
    }

    public String getShortName() {
        return shortName;
    }

    public Long getCreatedUtc() {
        return createdUtc;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }
}
