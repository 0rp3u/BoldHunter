package pt.boldint.carbon.boldhunter.data.api

import kotlinx.coroutines.experimental.Deferred
import pt.boldint.carbon.boldhunter.data.api.inputmodel.*
import retrofit2.http.*

interface HunterService {

    @GET("posts/all")
    fun getPosts(
            @Query("page") page:Int = 1,
            @Query("per_page") per_page:Int = 10,
            @Query("sort_by") sort_by:String = "created_at",
            @Query("order") order: String = "desc",
            @Query("year") year:Int?,
            @Query("month") month:Int?,
            @Query("day") day:Int?
    ): Deferred<PostList>

    @GET("posts/{id}")
    fun getPostDetails(@Path("id") id: Int): Deferred<PostDetailsResponse>

    @GET("users/{id}")
    fun getUserDetails(@Path("id") id: Int): Deferred<User>

    @GET("post/{id}/comments")
    fun getPostComments(@Path("id") id: Int): Deferred<CommentList>
}
