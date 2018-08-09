package pt.boldint.carbon.boldhunter.presenter.userdetails

import android.util.Log
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.boldint.carbon.boldhunter.interactor.post.PostInteractor
import pt.boldint.carbon.boldhunter.interactor.user.UserInteractor
import pt.boldint.carbon.boldhunter.presenter.base.BasePresenterImpl
import pt.boldint.carbon.boldhunter.view.userdetails.UserDetailsView


class UserDetailsPresenterImpl(private val userInteractor: UserInteractor) : BasePresenterImpl<UserDetailsView>(), UserDetailsPresenter {

    private val jobs = Job()

    override fun setUser(id: Int) {
        launch (UI, parent =jobs) {
            view?.showLoadingIndicator()
            try {
                val user = userInteractor.getUserDetails(id)

                view?.showUser(user)

                val userPosts = userInteractor.getUserPosts(id)

                view?.showUserPosts(userPosts)

                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                Log.e(TAG, e.stackTrace?.contentDeepToString())
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") { setUser(id) }
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }

    companion object {
        val TAG = UserDetailsPresenterImpl::class.simpleName
    }
}