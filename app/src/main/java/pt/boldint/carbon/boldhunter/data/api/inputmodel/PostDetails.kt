package pt.boldint.carbon.boldhunter.data.api.inputmodel

data class PostDetails(
        val comments_count : Int,
        val day: String?,
        val id: Int,
        val user: User,
        val name: String,
        val tagline: String,
        val votes_count: Int,
        val description: String,
        val thumbnail: Thumbnail,
        val media: List<Media>,
        val votes: List<Vote>,
        val related_posts: List<Post>,
        val comments: List<Comment>
)