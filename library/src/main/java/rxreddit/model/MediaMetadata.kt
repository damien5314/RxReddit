package rxreddit.model

import com.google.gson.annotations.SerializedName

data class MediaMetadata(
    @SerializedName("status")
    val status: String,
    @SerializedName("e")
    val e: String,
    @SerializedName("m")
    val m: String, // encoding?
    @SerializedName("p")
    val p: List<MediaMetadataPreview>,
    @SerializedName("s")
    val s: MediaMetadataPreview,
)

data class MediaMetadataPreview(
    @SerializedName("x")
    val x: Int,
    @SerializedName("y")
    val y: Int,
    @SerializedName("u")
    val u: String,
)
