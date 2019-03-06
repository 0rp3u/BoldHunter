package pt.boldint.carbon.boldhunter.data.api.inputmodel



data class Post(
        val id: Int,
        val comments_count : Int,
        val day: String?,
        val name: String,
        val user: User,
        val tagline: String?,
        val votes_count: Int,
        val thumbnail: Thumbnail
        )