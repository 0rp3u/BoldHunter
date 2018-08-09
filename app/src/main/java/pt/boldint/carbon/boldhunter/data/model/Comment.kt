package pt.boldint.carbon.boldhunter.data.model

data class Comment(
        val id: Int,
        val body: String,
        val parent_comment_id: String?,
        val post_id: Int,
        val votes:Int,
        val user: User,
        val replies: List<Comment>
)