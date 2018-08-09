package pt.boldint.carbon.boldhunter.presenter

import pt.boldint.carbon.boldhunter.presenter.base.BasePresenter
import pt.boldint.carbon.boldhunter.view.main.MainView


interface MainPresenter : BasePresenter<MainView> {

    fun setPosts(
            page:Int = 1,
            per_page:Int = 10,
            sort_by:String = "created_at",
            order: String = "desc",
            year:Int?,
            month:Int?,
            day:Int?
    )

}

