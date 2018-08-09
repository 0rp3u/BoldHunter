package pt.boldint.carbon.boldhunter.presenter.userdetails

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.boldint.carbon.boldhunter.data.api.HunterService
import pt.boldint.carbon.boldhunter.presenter.base.BasePresenterImpl
import pt.boldint.carbon.boldhunter.view.postdetails.UserDetailsView


class UserDetailsPresenterImpl(private val service: HunterService) : BasePresenterImpl<UserDetailsView>(), UserDetailsPresenter {

    private val jobs = Job()

    override fun setUser(id: Int) {
        launch (UI, parent =jobs) {
            view?.showLoadingIndicator()
            try {
                val post = service.getUserDetails(id).await()

                view?.showUser(post)
                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") { setUser(id) }
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}