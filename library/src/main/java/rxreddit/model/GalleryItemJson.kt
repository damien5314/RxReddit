package rxreddit.model

import com.google.gson.annotations.SerializedName

data class GalleryItems(
    @SerializedName("items")
    val items: List<GalleryItemJson>,
)

data class GalleryItemJson(
    @SerializedName("id")
    val id: String,
    @SerializedName("media_id")
    val mediaId: String,
)
