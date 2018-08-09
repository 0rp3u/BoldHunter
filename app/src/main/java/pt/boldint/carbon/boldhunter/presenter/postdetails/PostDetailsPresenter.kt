package pt.boldint.carbon.boldhunter.presenter.postdetails

import pt.boldint.carbon.boldhunter.presenter.base.BasePresenter
import pt.boldint.carbon.boldhunter.view.postdetails.PostDetailsView


interface PostDetailsPresenter : BasePresenter<PostDetailsView> {

    fun setPost(id: Int)

}

