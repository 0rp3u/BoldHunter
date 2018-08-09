package pt.boldint.carbon.boldhunter.presenter.postdetails

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import org.jetbrains.anko.coroutines.experimental.asReference
import pt.boldint.carbon.boldhunter.data.api.HunterService
import pt.boldint.carbon.boldhunter.interactor.post.PostInteractor
import pt.boldint.carbon.boldhunter.presenter.base.BasePresenterImpl
import pt.boldint.carbon.boldhunter.view.postdetails.PostDetailsView



class PostDetailsPresenterImpl(private val postInteractor: PostInteractor) : BasePresenterImpl<PostDetailsView>(), PostDetailsPresenter {

    private val jobs = Job()

    override fun setPost(id: Int) {
        launch (UI, parent =jobs) {
            view?.showLoadingIndicator()
            try {
                val post = postInteractor.getPostDetails(id)

                view?.showPost(post)

                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                throw e
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") { setPost(id) }
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}