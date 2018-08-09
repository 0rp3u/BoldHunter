package pt.boldint.carbon.boldhunter.presenter.userdetails

import pt.boldint.carbon.boldhunter.presenter.base.BasePresenter
import pt.boldint.carbon.boldhunter.view.postdetails.UserDetailsView


interface UserDetailsPresenter : BasePresenter<UserDetailsView> {

    fun setUser(id: Int)

}

