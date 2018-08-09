package pt.boldint.carbon.boldhunter.presenter.base

import pt.boldint.carbon.boldhunter.view.base.BaseView

abstract class BasePresenterImpl<V: BaseView> : BasePresenter<V> {

    protected var view: V? = null

    override fun onViewAttached(view: V) {
        this.view = view
    }

    override fun onViewDetached() {
        this.cancelRequest()
    }
}
