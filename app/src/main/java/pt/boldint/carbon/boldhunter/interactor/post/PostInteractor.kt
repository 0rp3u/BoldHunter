package pt.boldint.carbon.boldhunter.interactor.post

import pt.boldint.carbon.boldhunter.data.model.OrderBy
import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.data.model.PostDetails
import pt.boldint.carbon.boldhunter.data.model.SortBy

interface PostInteractor{

    suspend fun getPostDetails(id:Int) : PostDetails

    suspend fun getPosts(page: Int, per_page: Int, sort_by: SortBy, order: OrderBy, year: Int?, month: Int?, day: Int?):List<Post>

}
