package rxreddit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Link extends Listing implements Votable, Savable, Hideable {
  @Expose
  Data data;

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

  public List<Object> getUserReports() {
    return data.userReports;
  }

  public Object getSecureMedia() {
    return data.secureMedia;
  }

  public Object getLinkFlairText() {
    return data.linkFlairText;
  }

  public Integer getGilded() {
    return data.gilded;
  }

  @Override
  public boolean isArchived() {
    return data.isArchived == null ? false : data.isArchived;
  }

  public boolean getClicked() {
    return data.clicked == null ? false : data.clicked;
  }

  public Object getReportReasons() {
    return data.reportReasons;
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
      case 0: isLiked(null); break;
      case 1: isLiked(true); break;
      case -1: isLiked(false); break;
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

  @Override
  public boolean isHidden() {
    return data.hidden == null ? false : data.hidden;
  }

  @Override
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

  public Data.SecureMediaEmbed getSecureMediaEmbed() {
    return data.secureMediaEmbed;
  }

  @Override
  public boolean isSaved() {
    return data.saved == null ? false : data.saved;
  }

  @Override
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

  public List<Object> getModReports() {
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

  public static class Data extends ListingData {
    @Expose
    private Preview preview; // New field for preview images
    @Expose
    private String domain;
    @SerializedName("banned_by")
    private Object bannedBy;
    @SerializedName("media_embed")
    private MediaEmbed mediaEmbed;
    @Expose
    private String subreddit;
    @SerializedName("selftext_html")
    private String selftextHtml;
    @Expose
    private String selftext;
    @Expose
    private String edited;
    @Expose
    private Boolean likes;
    @SerializedName("user_reports")
    private List<Object> userReports;
    @SerializedName("secure_media")
    private Object secureMedia;
    @SerializedName("link_flair_text")
    private Object linkFlairText;
    @Expose
    private Integer gilded;
    @Expose @SerializedName("archived")
    private Boolean isArchived;
    private Boolean clicked;
    @SerializedName("report_reasons")
    private Object reportReasons;
    @Expose
    private String author;
    @Expose @SerializedName("num_comments")
    private Integer numComments;
    @Expose
    private Integer score;
    @SerializedName("approved_by")
    private Object approvedBy;
    @Expose @SerializedName("over_18")
    private Boolean over18;
    @Expose
    private Boolean hidden;
    @Expose
    private String thumbnail;
    @SerializedName("subreddit_id")
    private String subredditId;
    @Expose @SerializedName("hide_score")
    private Boolean hideScore;
    @SerializedName("link_flair_css_class")
    private Object linkFlairCssClass;
    @SerializedName("author_flair_css_class")
    private Object authorFlairCssClass;
    private Integer downs;
    @SerializedName("secure_media_embed")
    private SecureMediaEmbed secureMediaEmbed;
    @Expose
    private Boolean saved;
    @Expose
    private Boolean stickied;
    @Expose @SerializedName("is_self")
    private Boolean isSelf;
    @Expose
    private String permalink;
    private Double created;
    @Expose
    private String url;
    @SerializedName("author_flair_text")
    private Object authorFlairText;
    @Expose
    private String title;
    @Expose @SerializedName("created_utc")
    private Double createdUtc;
    @Expose
    private String distinguished;
    private Media media;
    @SerializedName("mod_reports")
    private List<Object> modReports;
    private Boolean visited;
    @SerializedName("num_reports")
    private Object numReports;
    private Integer ups;

    public static class SecureMediaEmbed { }

    public static class MediaEmbed {
      @Expose
      private String content;
      @Expose
      private Integer width;
      @Expose
      private Boolean scrolling;
      @Expose
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
      @Expose
      private Oembed oembed;
      @Expose
      private String type;

      public Oembed getOembed() {
        return oembed;
      }

      public String getType() {
        return type;
      }

      public static class Oembed {
        @Expose @SerializedName("provider_url")
        private String providerUrl;
        @Expose
        private String description;
        @Expose
        private String title;
        @Expose
        private String type;
        @Expose @SerializedName("thumbnail_width")
        private Integer thumbnailWidth;
        @Expose
        private Integer height;
        @Expose
        private Integer width;
        @Expose
        private String html;
        @Expose
        private String version;
        @Expose @SerializedName("provider_name")
        private String providerName;
        @Expose @SerializedName("thumbnail_url")
        private String thumbnailUrl;
        @Expose @SerializedName("thumbnail_height")
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
    }
  }

  public static class Preview {
    @Expose
    List<Image> images;

    public static class Image {
      @Expose
      Res source;
      @Expose List<Res> resolutions;
      @Expose Variants variants;
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
        @Expose public Image nsfw;
      }

      public static class Res {
        @Expose String url;
        @Expose int width;
        @Expose int height;

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
}
