package pt.boldint.carbon.boldhunter.presenter.main

import android.util.Log
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.boldint.carbon.boldhunter.interactor.post.PostInteractor
import pt.boldint.carbon.boldhunter.presenter.MainPresenter
import pt.boldint.carbon.boldhunter.presenter.base.BasePresenterImpl
import pt.boldint.carbon.boldhunter.view.main.MainView


class MainPresenterImpl(private val postInteractor: PostInteractor) : BasePresenterImpl<MainView>(), MainPresenter {

    private val jobs = Job()

    override fun setPosts(page: Int, per_page: Int, sort_by: String, order: String, year: Int?, month: Int?, day: Int?) {
        launch (UI, parent =jobs) {
            view?.showLoadingIndicator()
            try {
                val posts = postInteractor.getPosts(page,per_page, sort_by, order, year, month, day)

                view?.showPosts(posts)
                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                throw e
                Log.e(this@MainPresenterImpl.javaClass.name, e.message)
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") {setPosts(page, per_page, sort_by, order, year, month, day) }
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}