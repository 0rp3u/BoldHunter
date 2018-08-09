package pt.boldint.carbon.boldhunter.data.model

import android.net.Uri

data class UserDetails(
        val id: Int,
        val name: String,
        val username: String,
        val headline: String,
        val twitter_username: String?,
        val website_url: Uri?,
        val user_image_url: Uri

)