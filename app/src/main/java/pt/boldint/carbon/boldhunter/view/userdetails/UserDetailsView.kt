package pt.boldint.carbon.boldhunter.view.postdetails

import android.view.View
import pt.boldint.carbon.boldhunter.data.api.inputmodel.User
import pt.boldint.carbon.boldhunter.view.base.BaseView

interface UserDetailsView : BaseView {


    fun showUser(user: User)

    fun showLoadingIndicator()
    fun hideLoadingIndicator()
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
}