package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class MediaEmbed {

    @SerializedName("content") String content;
    @SerializedName("width") Integer width;
    @SerializedName("scrolling") Boolean scrolling;
    @SerializedName("height") Integer height;

    public String getContent() {
        return content;
    }

    public Integer getWidth() {
        return width;
    }

    public Boolean getScrolling() {
        return scrolling;
    }

    public Integer getHeight() {
        return height;
    }
}
