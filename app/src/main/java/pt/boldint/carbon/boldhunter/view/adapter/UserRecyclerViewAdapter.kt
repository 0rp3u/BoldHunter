package pt.boldint.carbon.boldhunter.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*
import pt.boldint.carbon.boldhunter.R
import pt.boldint.carbon.boldhunter.data.model.User
import pt.boldint.carbon.boldhunter.extension.removeQuery

class UserRecyclerViewAdapter(val listener: (User) -> Unit) : RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

    private val historyList = mutableListOf<User>()

    fun addItems(items: List<User>){
        historyList.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history, listener)
    }

    override fun getItemCount() = historyList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User, listener: (User) -> Unit) {

            Picasso.get()
                    .load(user.user_image_url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(itemView.user_image)

            itemView.username.text = user.username
            itemView.name.text = user.name

            itemView.desc.text = user.headline
            itemView.setOnClickListener { listener(user) }
        }
    }

}
