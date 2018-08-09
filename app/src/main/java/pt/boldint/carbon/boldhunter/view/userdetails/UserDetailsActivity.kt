package pt.boldint.carbon.boldhunter.view.userdetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.progress_bar.*
import pt.boldint.carbon.boldhunter.R
import pt.boldint.carbon.boldhunter.data.api.inputmodel.User
import pt.boldint.carbon.boldhunter.extension.app
import pt.boldint.carbon.boldhunter.extension.longSnackbar
import pt.boldint.carbon.boldhunter.extension.snackbar
import pt.boldint.carbon.boldhunter.presenter.userdetails.UserDetailsPresenter
import pt.boldint.carbon.boldhunter.view.adapter.PostRecyclerViewAdapter
import pt.boldint.carbon.boldhunter.view.base.BaseActivity
import pt.boldint.carbon.boldhunter.view.postdetails.PostDetailsActivity
import pt.boldint.carbon.boldhunter.view.postdetails.UserDetailsView
import javax.inject.Inject

class UserDetailsActivity : BaseActivity<UserDetailsPresenter, UserDetailsView>(), UserDetailsView {

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
    }

    lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    @Inject
    override lateinit var presenter: UserDetailsPresenter

    override fun injectDependencies() {
        app.applicationComponent
                .presenters()
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        postRecyclerViewAdapter = PostRecyclerViewAdapter {
            startActivity(
                    Intent(this, PostDetailsActivity::class.java)
                            .run { putExtra(PostDetailsActivity.EXTRA_POST_ID, it.id) }
            )
        }


        posts_recycler_view.apply {
            adapter = postRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@UserDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        }


        presenter.setUser(this.intent.getIntExtra(EXTRA_USER_ID,1))

    }

    override fun showUser(user: User) {
        title = "${user.name}'s info"

       // postRecyclerViewAdapter.addItems()

    }

    override fun showLoadingIndicator() {
        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if (action != null)
            longSnackbar(post_details, error ?: "error, something when wrong", "repeat?", action )
        else
            snackbar(post_details, error ?: "error, something when wrong")
    }
}
