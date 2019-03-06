package pt.boldint.carbon.boldhunter.presenter.main

import pt.boldint.carbon.boldhunter.data.model.OrderBy
import pt.boldint.carbon.boldhunter.data.model.SortBy
import pt.boldint.carbon.boldhunter.presenter.base.BasePresenter
import pt.boldint.carbon.boldhunter.view.main.MainView


interface MainPresenter : BasePresenter<MainView> {

    fun setPosts(
            from_page:Int = 1,
            to_page:Int = 1,
            per_page:Int = 10,
            sort_by:SortBy = SortBy.created_at,
            order: OrderBy = OrderBy.desc,
            year:Int?,
            month:Int?,
            day:Int?
    )

}

