package pt.boldint.carbon.boldhunter.view.main

import android.view.View
import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.view.base.BaseView

interface MainView : BaseView {


    fun showPosts(posts: List<Post>)

    fun showLoadingIndicator()
    fun hideLoadingIndicator()
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
}