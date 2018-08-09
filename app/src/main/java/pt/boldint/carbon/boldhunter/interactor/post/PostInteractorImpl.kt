package pt.boldint.carbon.boldhunter.interactor.post
import pt.boldint.carbon.boldhunter.data.api.HunterService
import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.data.toDomain


class PostInteractorImpl(private val service: HunterService) : PostInteractor {

    override suspend fun getPostDetails(id:Int) = service.getPostDetails(id).await().toDomain()

    override suspend fun getPosts(page: Int, per_page: Int, sort_by: String, order: String, year: Int?, month: Int?, day: Int?): List<Post> =
        service.getPosts(page, per_page, sort_by, order, year, month, day).await().toDomain()

}
