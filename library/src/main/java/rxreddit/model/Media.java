package rxreddit.model;

import com.google.gson.annotations.SerializedName;

public class Media {

    @SerializedName("oembed")
    Oembed oembed;
    @SerializedName("type")
    String type;
    @SerializedName("reddit_video")
    RedditVideo redditVideo;

    public Oembed getOembed() {
        return oembed;
    }

    public String getType() {
        return type;
    }

    public RedditVideo getRedditVideo() {
        return redditVideo;
    }

    public static class Oembed {

        @SerializedName("provider_url")
        private String providerUrl;
        private String description;
        private String title;
        private String type;
        @SerializedName("thumbnail_width")
        private Integer thumbnailWidth;
        private Integer height;
        private Integer width;
        private String html;
        private String version;
        @SerializedName("provider_name")
        private String providerName;
        @SerializedName("thumbnail_url")
        private String thumbnailUrl;
        @SerializedName("thumbnail_height")
        private Integer thumbnailHeight;

        public String getProviderUrl() {
            return providerUrl;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public Integer getThumbnailWidth() {
            return thumbnailWidth;
        }

        public Integer getHeight() {
            return height;
        }

        public Integer getWidth() {
            return width;
        }

        public String getHtml() {
            return html;
        }

        public String getVersion() {
            return version;
        }

        public String getProviderName() {
            return providerName;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public Integer getThumbnailHeight() {
            return thumbnailHeight;
        }
    }

    public static class RedditVideo {

        @SerializedName("fallback_url")
        String fallbackUrl;
        @SerializedName("height")
        int height;
        @SerializedName("width")
        int width;
        @SerializedName("scrubber_media_url")
        String scrubberMediaUrl;
        @SerializedName("dash_url")
        String dashUrl;
        @SerializedName("duration")
        int duration;
        @SerializedName("hls_url")
        String hlsUrl;
        @SerializedName("is_gif")
        boolean isGif;
        @SerializedName("transcoding_status")
        String transcodingStatus;

        public String getFallbackUrl() {
            return fallbackUrl;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public String getScrubberMediaUrl() {
            return scrubberMediaUrl;
        }

        public String getDashUrl() {
            return dashUrl;
        }

        public int getDuration() {
            return duration;
        }

        public String getHlsUrl() {
            return hlsUrl;
        }

        public boolean isGif() {
            return isGif;
        }

        public String getTranscodingStatus() {
            return transcodingStatus;
        }
    }
}
