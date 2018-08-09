package pt.boldint.carbon.boldhunter.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import pt.boldint.carbon.boldhunter.R
import pt.boldint.carbon.boldhunter.extension.removeQuery


class ImagesPagerAdapter(val mContext: Context, val imageUrl: List<Uri>) : PagerAdapter() {

    override fun getCount(): Int {
        return imageUrl.size
    }

    override fun isViewFromObject(view: View, obj : Any): Boolean {
        return view === obj as ImageView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
                return ImageView(mContext).apply {
                    Picasso.get()
                            .load(imageUrl[position])
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.ic_broken_image_black_24dp)
                            .into(this)
                    container.addView(this)
                }
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj : Any) {
        container.removeView(obj as ImageView)
    }
}