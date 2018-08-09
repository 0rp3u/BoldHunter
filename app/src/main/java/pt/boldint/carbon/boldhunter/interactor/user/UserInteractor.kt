package pt.boldint.carbon.boldhunter.interactor.user

import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.data.model.UserDetails

interface UserInteractor{

    suspend fun getUserDetails(id:Int) : UserDetails

    suspend fun getUserPosts(id:Int) : List<Post>

}
