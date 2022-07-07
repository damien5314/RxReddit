package rxreddit.model

import com.google.gson.annotations.SerializedName

data class GalleryItemJson(
    @SerializedName("id")
    val id: String,
    @SerializedName("media_id")
    val mediaId: String,
)
