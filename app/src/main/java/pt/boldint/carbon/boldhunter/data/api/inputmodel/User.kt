package pt.boldint.carbon.boldhunter.data.api.inputmodel

data class User(
        val id: Int,
        val created_at: String,
        val name: String,
        val username: String,
        val headline: String?,
        val twitter_username: String?,
        val website_url: String,
        val image_url:ImageUrl

)