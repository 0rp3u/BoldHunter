package pt.boldint.carbon.boldhunter.view.userdetails

import android.view.View
import pt.boldint.carbon.boldhunter.data.api.inputmodel.User
import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.data.model.UserDetails
import pt.boldint.carbon.boldhunter.view.base.BaseView

interface UserDetailsView : BaseView {


    fun showUser(user: UserDetails)

    fun showUserPosts(posts: List<Post>)

    fun showLoadingIndicator()
    fun hideLoadingIndicator()
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
}