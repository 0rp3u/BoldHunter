package pt.boldint.carbon.boldhunter.data.api.inputmodel

data class Comment(
        val id: Int,
        val body: String,
        val parent_comment_id: String?,
        val user_id: Int,
        val child_comments_count: Int,
        val post_id: Int,
        val votes:Int,
        val user: User,
        val child_comments: List<Comment>
)