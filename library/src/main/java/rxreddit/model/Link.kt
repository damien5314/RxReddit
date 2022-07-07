package rxreddit.model

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("data") val data: Data,
) : Listing(), Votable, Savable, Hideable {

    override val id: String
        get() = data.id

    val domain: String?
        get() = data.domain

    val mediaEmbed: MediaEmbed?
        get() = data.mediaEmbed

    val subreddit: String?
        get() = data.subreddit

    val selftextHtml: String?
        get() = data.selftextHtml

    val selftext: String?
        get() = data.selftext

    val edited: Boolean
        get() = data.edited ?: false

    override var liked: Boolean? by data::liked

    val userReports: List<UserReport>?
        get() = data.userReports

    val linkFlairText: Any?
        get() = data.linkFlairText

    val gilded: Int?
        get() = data.gilded

    override val archived: Boolean
        get() = data.isArchived ?: false

    val clicked: Boolean
        get() = data.clicked ?: false

    val author: String?
        get() = data.author

    val numComments: Int?
        get() = data.numComments

    var score: Int? by data::score

    override fun applyVote(direction: Int) {
        val scoreDiff = direction - likedScore
        data.liked = when (direction) {
            0 -> null
            1 -> true
            -1 -> false
            else -> null
        }
        if (data.score == null) return
        data.score = data.score?.plus(scoreDiff) ?: 0
    }

    private val likedScore: Int
        get() = if (liked == null) 0 else if (liked == true) 1 else -1

    val approvedBy: Any?
        get() = data.approvedBy

    val over18: Boolean
        get() = data.over18 ?: false

    override var hidden: Boolean
        get() = data.hidden ?: false
        set(value) {
            data.hidden = value
        }

    val thumbnail: String?
        get() = data.thumbnail

    val subredditId: String?
        get() = data.subredditId

    val isScoreHidden: Boolean
        get() = data.hideScore ?: false

    val linkFlairCssClass: Any?
        get() = data.linkFlairCssClass

    val authorFlairCssClass: Any?
        get() = data.authorFlairCssClass

    val downs: Int?
        get() = data.downs

    override var isSaved: Boolean
        get() = if (data.saved == null) false else data.saved!!
        set(value) {
            data.saved = value
        }

    val stickied: Boolean
        get() = data.stickied ?: false

    val isSelf: Boolean
        get() = data.isSelf ?: false

    val permalink: String?
        get() = data.permalink

    val created: Double?
        get() = data.created

    val url: String?
        get() = data.url

    val authorFlairText: Any?
        get() = data.authorFlairText

    val title: String?
        get() = data.title

    val createdUtc: Double?
        get() = data.createdUtc

    val distinguished: String?
        get() = data.distinguished

    val media: Media?
        get() = data.media

    val modReports: List<ModReport>?
        get() = data.modReports

    val visited: Boolean
        get() = data.visited ?: false

    val numReports: Any?
        get() = data.numReports

    val ups: Int?
        get() = data.ups

    val previewImages: List<Image>?
        get() = if (data.preview == null) null else data.preview!!.images

    data class Data(
        @SerializedName("preview")
        val preview: Preview? = null,

        @SerializedName("domain")
        val domain: String? = null,

        @SerializedName("banned_by")
        val bannedBy: String? = null,

        @SerializedName("media_embed")
        val mediaEmbed: MediaEmbed? = null,

        @SerializedName("subreddit")
        val subreddit: String? = null,

        @SerializedName("selftext_html")
        val selftextHtml: String? = null,

        @SerializedName("selftext")
        val selftext: String? = null,

        @SerializedName("edited")
        val edited: Boolean?,

        // TODO: Remove mutability here
        @SerializedName("likes")
        var liked: Boolean? = null,

        @SerializedName("user_reports")
        val userReports: List<UserReport>? = null,

        @SerializedName("link_flair_text")
        val linkFlairText: String? = null,

        @SerializedName("gilded")
        val gilded: Int? = null,

        @SerializedName("archived")
        val isArchived: Boolean? = null,

        @SerializedName("clicked")
        val clicked: Boolean? = null,

        @SerializedName("author")
        val author: String? = null,

        @SerializedName("num_comments")
        val numComments: Int? = null,

        // TODO: Remove mutability here
        @SerializedName("score")
        var score: Int? = null,

        @SerializedName("approved_by")
        val approvedBy: String? = null,

        @SerializedName("over_18")
        val over18: Boolean? = null,

        // TODO: Remove mutability here
        @SerializedName("hidden")
        var hidden: Boolean? = null,

        @SerializedName("thumbnail")
        val thumbnail: String? = null,

        @SerializedName("subreddit_id")
        val subredditId: String? = null,

        @SerializedName("hide_score")
        val hideScore: Boolean? = null,

        @SerializedName("link_flair_css_class")
        val linkFlairCssClass: String? = null,

        @SerializedName("author_flair_css_class")
        val authorFlairCssClass: String? = null,

        @SerializedName("downs")
        val downs: Int? = null,

        // TODO: Remove mutability here
        @SerializedName("saved")
        var saved: Boolean? = null,

        @SerializedName("stickied")
        val stickied: Boolean? = null,

        @SerializedName("is_self")
        val isSelf: Boolean? = null,

        @SerializedName("permalink")
        val permalink: String? = null,

        @SerializedName("created")
        val created: Double? = null,

        @SerializedName("url")
        val url: String? = null,

        @SerializedName("author_flair_text")
        val authorFlairText: String? = null,

        @SerializedName("title")
        val title: String? = null,

        @SerializedName("created_utc")
        val createdUtc: Double? = null,

        @SerializedName("distinguished")
        val distinguished: String? = null,

        @SerializedName("media")
        val media: Media? = null,

        @SerializedName("mod_reports")
        val modReports: List<ModReport>? = null,

        @SerializedName("visited")
        val visited: Boolean? = null,

        @SerializedName("num_reports")
        val numReports: Int? = null,

        @SerializedName("ups")
        val ups: Int? = null,

        @SerializedName("is_gallery")
        val isGallery: Boolean? = null,
    ) : ListingData()

    data class Preview(
        @SerializedName("images")
        val images: List<Image>? = emptyList(),
    )
}
