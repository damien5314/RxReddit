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
        @SerializedName("secure_media")
        private SecureMedia secureMedia;
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

        public static class MediaEmbed {

            private String content;
            private Integer width;
            private Boolean scrolling;
            private Integer height;

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
        public static class Media {

            @SerializedName("oembed") Oembed oembed;
            @SerializedName("type") String type;
            @SerializedName("reddit_video") RedditVideo redditVideo;

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

                @SerializedName("fallback_url") String fallbackUrl;
                @SerializedName("height") int height;
                @SerializedName("width") int width;
                @SerializedName("scrubber_media_url") String scrubberMediaUrl;
                @SerializedName("dash_url") String dashUrl;
                @SerializedName("duration") int duration;
                @SerializedName("hls_url") String hlsUrl;
                @SerializedName("is_gif") boolean isGif;
                @SerializedName("transcoding_status") String transcodingStatus;

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
    }

    public static class Preview {

        List<Image> images;

        public static class Image {

            Res source;
            List<Res> resolutions;
            Variants variants;
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

                public Image nsfw;
            }

            public static class Res {

                String url;
                int width;
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
    }

    public static class SecureMedia {

    }

    //region Public API

    @Override
    public String getId() {
        return data.id;
    }

    public String getDomain() {
        return data.domain;
    }

    public Data.MediaEmbed getMediaEmbed() {
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

    public SecureMedia getSecureMedia() {
        return data.secureMedia;
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

    public Data.Media getMedia() {
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

    public List<Preview.Image> getPreviewImages() {
        if (data.preview == null)
            return null;
        return data.preview.images;
    }

    //endregion
}
