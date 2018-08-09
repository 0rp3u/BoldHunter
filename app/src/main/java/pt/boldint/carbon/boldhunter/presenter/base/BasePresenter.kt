package pt.boldint.carbon.boldhunter.presenter.base

interface BasePresenter<in V> {

    /**
     * Called when the view is attached to the detailsPresenter.
     *
     * @param view the view.
     */
    fun onViewAttached(view: V)

    /**
     * Called when the view is detached from the detailsPresenter.
     */
    fun onViewDetached()

    /**
     * Called when view is not interested on the data anymore
     */
    fun cancelRequest()
}
