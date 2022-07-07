package rxreddit.model

import com.google.gson.annotations.SerializedName

abstract class Listing {

    @SerializedName("kind")
    var kind: String? = null

    abstract val id: String

    val fullName: String
        get() = kind + "_" + id
}
