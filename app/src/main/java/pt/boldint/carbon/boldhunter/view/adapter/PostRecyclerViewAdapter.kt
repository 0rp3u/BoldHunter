package pt.boldint.carbon.boldhunter.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_post.view.*
import pt.boldint.carbon.boldhunter.R
import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.view.userdetails.UserDetailsActivity

class PostRecyclerViewAdapter(val listener: (Post) -> Unit) : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>() {

    private val historyList = mutableListOf<Post>()

    fun addItems(items: List<Post>){
        historyList.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        historyList.removeAll { true }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history, listener)
    }

    override fun getItemCount() = historyList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post, listener: (Post) -> Unit) {
            Picasso.get()
                    .load(post.thumbnail_url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(itemView.thumbnail)

            Picasso.get()
                    .load(post.user.user_image_url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(itemView.user_image)

            itemView.username.text = post.user.username
            itemView.user_info.setOnClickListener {
                startActivity(itemView.context,
                        Intent(itemView.context, UserDetailsActivity::class.java)
                                .run { putExtra(UserDetailsActivity.EXTRA_USER_ID, post.user.id) },
                        null
                )
            }

            itemView.title.text = post.name
            itemView.subtitle.text = post.tagline
            itemView.upvote_number.text = "${post.votes_count}"
            itemView.comment_number.text = "${post.comments_count}"
            itemView.setOnClickListener { listener(post) }
        }

    }

}
