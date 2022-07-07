package rxreddit.model

interface Votable : Archivable {

    val kind: String?

    var liked: Boolean?

    fun applyVote(direction: Int)
}
