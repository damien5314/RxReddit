package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {

    @SerializedName("source")
    Res source;
    @SerializedName("resolutions")
    List<Res> resolutions;
    @SerializedName("variants")
    Variants variants;
    @SerializedName("id")
    String id;

    public Res getSource() {
        return source;
    }

    public List<Res> getResolutions() {
        return resolutions;
    }

    public Variants getVariants() {
        return variants;
    }

    public String getId() {
        return id;
    }

    public static class Variants {

        @SerializedName("nsfw")
        Image nsfw;

        public Image getNsfw() {
            return nsfw;
        }
    }

    public static class Res {

        @SerializedName("url")
        String url;
        @SerializedName("width")
        int width;
        @SerializedName("height")
        int height;

        public String getUrl() {
            return url;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
