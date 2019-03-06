package pt.boldint.carbon.boldhunter.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import pt.boldint.carbon.boldhunter.presenter.base.BasePresenter


abstract class BaseFragment<P: BasePresenter<V>, in V> : BaseView, Fragment() {

    abstract fun injectDependencies()

    open lateinit var presenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onStart() {
        super.onStart()
        @Suppress("UNCHECKED_CAST")
        presenter.onViewAttached(this as V)
    }

    override fun onStop() {
        super.onStop()
        presenter.onViewDetached()
    }
}
