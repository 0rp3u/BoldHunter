package pt.boldint.carbon.boldhunter.view.postdetails

import android.view.View
import pt.boldint.carbon.boldhunter.data.model.PostDetails
import pt.boldint.carbon.boldhunter.view.base.BaseView

interface PostDetailsView : BaseView {


    fun showPost(post: PostDetails)

    fun showLoadingIndicator()
    fun hideLoadingIndicator()
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
}