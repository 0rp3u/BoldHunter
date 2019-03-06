package pt.boldint.carbon.boldhunter.view.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import pt.boldint.carbon.boldhunter.R
import pt.boldint.carbon.boldhunter.presenter.base.BasePresenter


abstract class BaseActivity<P: BasePresenter<V>, in V> : BaseView, AppCompatActivity() {

    abstract fun injectDependencies() //<- injects P

    abstract var presenter: P

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
        presenter.onViewDetached()
        super.onStop()
    }
}
