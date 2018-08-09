package pt.boldint.carbon.boldhunter.view.postdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_details.*
import kotlinx.android.synthetic.main.layout_comment.view.*
import kotlinx.android.synthetic.main.progress_bar.*
import pt.boldint.carbon.boldhunter.R
import pt.boldint.carbon.boldhunter.data.model.Comment
import pt.boldint.carbon.boldhunter.data.model.PostDetails
import pt.boldint.carbon.boldhunter.data.model.User
import pt.boldint.carbon.boldhunter.extension.app
import pt.boldint.carbon.boldhunter.extension.longSnackbar
import pt.boldint.carbon.boldhunter.extension.removeQuery
import pt.boldint.carbon.boldhunter.extension.snackbar
import pt.boldint.carbon.boldhunter.presenter.postdetails.PostDetailsPresenter
import pt.boldint.carbon.boldhunter.view.adapter.ImagesPagerAdapter
import pt.boldint.carbon.boldhunter.view.adapter.PostRecyclerViewAdapter
import pt.boldint.carbon.boldhunter.view.adapter.UserRecyclerViewAdapter
import pt.boldint.carbon.boldhunter.view.base.BaseActivity
import pt.boldint.carbon.boldhunter.view.userdetails.UserDetailsActivity
import javax.inject.Inject

class PostDetailsActivity : BaseActivity<PostDetailsPresenter, PostDetailsView>(), PostDetailsView {

    companion object {
        val EXTRA_POST_ID = "EXTRA_USER_ID"
    }

    lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    lateinit var userRecyclerViewAdapter: UserRecyclerViewAdapter

    @Inject
    override lateinit var presenter: PostDetailsPresenter

    override fun injectDependencies() {
        app.applicationComponent
                .presenters()
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        initRecyclerViews()

        presenter.setPost(this.intent.getIntExtra(EXTRA_POST_ID,1))


    }

    private fun initRecyclerViews(){

        postRecyclerViewAdapter = PostRecyclerViewAdapter {
            startActivity(
                    Intent(this, PostDetailsActivity::class.java)
                            .run { putExtra(EXTRA_POST_ID, it.id) }
            )
        }

        userRecyclerViewAdapter = UserRecyclerViewAdapter {
            startActivity(
                    Intent(this, UserDetailsActivity::class.java)
                            .run { putExtra(UserDetailsActivity.EXTRA_USER_ID, it.id) }
            )
        }


        recycler_view.apply {
            adapter = postRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@PostDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        upvoters_recycler_view.apply {
            adapter = userRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@PostDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        }

    }

    override fun showPost(post: PostDetails) {
        description.text = post.description

        supportActionBar?.title = post.name
        supportActionBar?.subtitle = post.tagline

        showAuthor(post.user)

        postRecyclerViewAdapter.addItems(post.related_posts)

        userRecyclerViewAdapter.addItems(post.voters)

        pager.adapter = ImagesPagerAdapter(this, post.media.map { it.image_url })

        fillComments(this@PostDetailsActivity.post_comments, post.comments, post.user.id)


    }

    private fun showAuthor(user: User){
        author_info.setOnClickListener {
            startActivity(
                    Intent(this, UserDetailsActivity::class.java)
                            .run { putExtra(UserDetailsActivity.EXTRA_USER_ID, user.id) }
            )
        }

        Picasso.get()

                .load(user.user_image_url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(author_image)

        author_name.text = user.name
        author_username.text = user.username
        author_description.text = user.headline

    }

    private fun fillComments(view: ViewGroup, comments:List<Comment>, postAuthorId: Int){
        comments.forEach {
            layoutInflater.inflate(R.layout.layout_comment, null).apply {
                Picasso.get()
                        .load(it.user.user_image_url)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.ic_broken_image_black_24dp)
                        .into(thumbnail)

                user_info.setOnClickListener {_->
                    startActivity(
                            Intent(this@PostDetailsActivity, PostDetailsActivity::class.java)
                                    .run { putExtra(EXTRA_POST_ID, it.id) }
                    )
                }


                title.text = it.user.username
                if (postAuthorId == it.user.id) title.setTextColor(context.resources.getColor(R.color.accent_material_dark))

                comment.text = it.body
                upvote_number.text = "${it.votes}"

                if (it.replies.isNotEmpty()){
                    fillComments(this.replies, it.replies, postAuthorId)
                }
                view.addView(this)

            }

        }

    }

    override fun showLoadingIndicator() {
        post_details.isVisible = false
        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
        post_details.isVisible = true
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if (action != null)
            longSnackbar(post_details, error ?: "error, something when wrong", "repeat?", action)
        else
            snackbar(post_details, error ?: "error, something when wrong")
    }
}
