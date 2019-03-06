package pt.boldint.carbon.boldhunter.data.model

import android.net.Uri

data class PostDetails(
        val id: Int,
        val comments_count : Int,
        val day: String,
        val user: User,
        val name: String,
        val tagline: String,
        val votes_count: Int,
        val description: String,
        val thumbnail_url: Uri,
        val media: List<Media>,
        val voters: List<User>,
        val related_posts: List<Post>,
        val comments: List<Comment>
)