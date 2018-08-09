package pt.boldint.carbon.boldhunter.view.userdetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.progress_bar.*
import pt.boldint.carbon.boldhunter.R
import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.data.model.UserDetails
import pt.boldint.carbon.boldhunter.extension.app
import pt.boldint.carbon.boldhunter.extension.longSnackbar
import pt.boldint.carbon.boldhunter.extension.snackbar
import pt.boldint.carbon.boldhunter.presenter.userdetails.UserDetailsPresenter
import pt.boldint.carbon.boldhunter.view.adapter.PostRecyclerViewAdapter
import pt.boldint.carbon.boldhunter.view.adapter.UserRecyclerViewAdapter
import pt.boldint.carbon.boldhunter.view.base.BaseActivity
import pt.boldint.carbon.boldhunter.view.postdetails.PostDetailsActivity
import javax.inject.Inject

class UserDetailsActivity : BaseActivity<UserDetailsPresenter, UserDetailsView>(), UserDetailsView {

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
    }

    lateinit var submitedPostsRecyclerViewAdapter: PostRecyclerViewAdapter

    lateinit var upvotedRecyclerViewAdapter: PostRecyclerViewAdapter

    lateinit var followersRecyclerViewAdapter: UserRecyclerViewAdapter

    @Inject
    override lateinit var presenter: UserDetailsPresenter

    override fun injectDependencies() {
        app.applicationComponent
                .presenters()
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        initRecyclerViews()

        val userId = this.intent.getIntExtra(EXTRA_USER_ID,1)

        presenter.setUser(userId)

    }

    private fun initRecyclerViews(){
        submitedPostsRecyclerViewAdapter = PostRecyclerViewAdapter {
            startActivity(
                    Intent(this, PostDetailsActivity::class.java)
                            .run { putExtra(PostDetailsActivity.EXTRA_POST_ID, it.id) }
            )
        }

        upvotedRecyclerViewAdapter = PostRecyclerViewAdapter {
            startActivity(
                    Intent(this, PostDetailsActivity::class.java)
                            .run { putExtra(PostDetailsActivity.EXTRA_POST_ID, it.id) }
            )
        }

        followersRecyclerViewAdapter = UserRecyclerViewAdapter {
            startActivity(
                    Intent(this, UserDetailsActivity::class.java)
                            .run { putExtra(UserDetailsActivity.EXTRA_USER_ID, it.id) }
            )
        }

        posts_recycler_view.apply {
            adapter = submitedPostsRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@UserDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        upvoted_recycler_view.apply {
            adapter = submitedPostsRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@UserDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        followers_recycler_view.apply {
            adapter = followersRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@UserDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun showUser(user: UserDetails) {
        title = "${user.name}'s info"
        supportActionBar?.subtitle = user.username

        Picasso.get()
                .load(user.user_image_url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_account_box_black_24dp)
                .into(user_image)

        about.text = user.headline

        upvotedRecyclerViewAdapter.addItems(user.upvoted)

        followersRecyclerViewAdapter.addItems(user.followers)
    }

    override fun showUserPosts(posts: List<Post>){
        submitedPostsRecyclerViewAdapter.addItems(posts)
    }

    override fun showLoadingIndicator() {
        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if (action != null)
            longSnackbar(user_details_layout, error ?: "error, something when wrong", "repeat?", action )
        else
            snackbar(user_details_layout, error ?: "error, something when wrong")
    }
}
