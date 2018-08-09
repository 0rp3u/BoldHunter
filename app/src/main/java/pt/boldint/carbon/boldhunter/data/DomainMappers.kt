package pt.boldint.carbon.boldhunter.data

import android.net.Uri
import pt.boldint.carbon.boldhunter.data.api.inputmodel.PostDetails as ApiPostDetails
import pt.boldint.carbon.boldhunter.data.api.inputmodel.PostList
import pt.boldint.carbon.boldhunter.data.api.inputmodel.Post as ApiPost
import pt.boldint.carbon.boldhunter.data.api.inputmodel.User as ApiUser
import pt.boldint.carbon.boldhunter.data.api.inputmodel.Media as ApiMedia
import pt.boldint.carbon.boldhunter.data.model.*
import pt.boldint.carbon.boldhunter.extension.removeAllQueries
import pt.boldint.carbon.boldhunter.data.api.inputmodel.Comment as ApiComment

fun ApiUser.toDomain() = User(
        id,
        name,
        username,
        headline ?: "",
        twitter_username,
        try { Uri.parse(website_url) }catch (e:Exception){null},
        Uri.parse(image_url.original).removeAllQueries()
)


fun PostList.toDomain()= posts.map { it.toDomain() }

fun ApiPost.toDomain()= Post(
        id,
        comments_count,
        day,
        name,
        user.toDomain(),
        tagline,
        votes_count,
        Uri.parse(thumbnail.image_url).removeAllQueries()
)

fun ApiComment.toDomain() : Comment = Comment(
        id,
        body,
        parent_comment_id,
        post_id,
        votes,
        user.toDomain(),
        child_comments.map { it.toDomain() }
)


fun ApiMedia.toDomain() = Media(
        id,
        media_type,
        original_width,
        original_height,
        Uri.parse(image_url).removeAllQueries(),
        platform,
        video_id
)

fun ApiPostDetails.toDomain() = post.run { PostDetails(
       id,
        comments_count,
        day ?: "",
        user.toDomain(),
        name,
        tagline,
        votes_count,
        description,
        Uri.parse(thumbnail.image_url).removeAllQueries(),
        media.map { it.toDomain() },
        votes.map { it.user.toDomain() },
        related_posts.map { it.toDomain() },
        comments.map { it.toDomain() }
    )

}

