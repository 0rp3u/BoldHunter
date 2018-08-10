package pt.boldint.carbon.boldhunter.presenter.main

import android.util.Log
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.boldint.carbon.boldhunter.data.model.OrderBy
import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.data.model.SortBy
import pt.boldint.carbon.boldhunter.interactor.post.PostInteractor
import pt.boldint.carbon.boldhunter.presenter.base.BasePresenterImpl
import pt.boldint.carbon.boldhunter.view.main.MainView


class MainPresenterImpl(private val postInteractor: PostInteractor) : BasePresenterImpl<MainView>(), MainPresenter {

    private val jobs = Job()

    override fun setPosts(from_page: Int, to_page: Int, per_page: Int, sort_by: SortBy, order: OrderBy, year: Int?, month: Int?, day: Int?) {
        launch (UI, parent =jobs) {
            view?.showLoadingIndicator()
            view?.viewState?.loadingPages = true
            try {

                val posts = mutableListOf<Post>()

                for (i in from_page+1 ..to_page){
                   posts.addAll(postInteractor.getPosts(i,per_page, sort_by, order, year, month, day))
                }


                view?.viewState?.currentPage = from_page+1

                view?.showPosts(posts)
                view?.hideLoadingIndicator()
                view?.viewState?.loadingPages = false

            }catch (e: Throwable){
                Log.e(TAG, e.localizedMessage)
                view?.viewState?.loadingPages = false
                view?.hideLoadingIndicator()
                view?.showErrorMessage("something went wrong, ${e.message}") {setPosts(from_page, to_page, per_page, sort_by, order, year, month, day) }
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }

    companion object {
        val TAG = MainPresenterImpl::class.simpleName
    }
}