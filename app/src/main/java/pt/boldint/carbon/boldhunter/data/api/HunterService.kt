package pt.boldint.carbon.boldhunter.data.api

import kotlinx.coroutines.experimental.Deferred
import pt.boldint.carbon.boldhunter.data.api.inputmodel.*
import retrofit2.http.*

interface HunterService {

    @GET("posts/all")
    fun getPosts(
            @Query("page") page:Int,
            @Query("per_page") per_page:Int,
            @Query("sort_by") sort_by:String,
            @Query("order") order: String,
            @Query("year") year:Int?,
            @Query("month") month:Int?,
            @Query("day") day:Int?
    ): Deferred<PostList>

    @GET("posts/{id}")
    fun getPostDetails(@Path("id") id: Int): Deferred<PostDetailsResponse>

    @GET("users/{id}")
    fun getUserDetails(@Path("id") id: Int): Deferred<UserResponse>

    @GET("users/{id}/posts")
    fun getUserPosts(@Path("id") id: Int): Deferred<PostList>

    @GET("post/{id}/comments")
    fun getPostComments(@Path("id") id: Int): Deferred<CommentList>
}
