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
    fun getPostDetails(@Path("id") id: Int): Deferred<PostDetails>

    @GET("users/{id}")
    fun getUserDetails(@Path("id") id: Int): Deferred<User>

    @GET("post/{id}/comments")
    fun getPostComments(@Path("id") id: Int): Deferred<CommentList>
//
////
////    @GET("vehicle")
////    fun getVehicleList(): Deferred<List<Vehicle>>
////
////    @PUT("vehicle")
////    fun addVehicle( @Body vehicle: Vehicle): Deferred<Vehicle>
////
////    @GET("vehicle/{id}")
////    fun getVehicleDetails(@Path("id") id: Int): Deferred<Vehicle>
//
//    @GET("vehicle/{id}/transaction")
//    fun getVehicleTransactions(@Path("id") id: Int): Deferred<List<TollingTransaction>>
//
//    @GET("vehicle/{id}/transaction/open")
//    fun getVehicleOpenTransactions(@Path("id") id: Int):Deferred<List<TollingTransaction>>
//
//    @GET("vehicle/{id}/transaction/closed")
//    fun getVehicleCLosedTransactions(@Path("id") id: Int): Deferred<List<TollingTransaction>>
//
//    @GET("transaction")
//    fun getTransactionList(@Query("owner") owner: String): Deferred<List<TollingTransaction>>
//
//    @GET("transaction/closed")
//    fun getClosedTransactionList(@Query("owner") owner: String): Deferred<List<TollingTransaction>>
//
//    @GET("transaction/open")
//    fun getOpenTransactionList(): Deferred<List<TollingTransaction>>
//
//    @POST("transaction/new")
//    fun initiateTollingTransaction(@Body transaction: TollingTransaction): Deferred<TollingTransaction>
//
//    @POST("transaction/close")
//    fun closeTollingTransaction( @Body transaction: TollingTransaction): Deferred<TollingTransaction>
//
//    @POST("transaction/cancel")
//    fun cancelTollingTransaction( @Body transaction: TollingTransaction): Deferred<TollingTransaction>



}
