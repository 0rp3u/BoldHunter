package pt.boldint.carbon.boldhunter.data.api.inputmodel

data class Media(

    val id: Int,
    val media_type: String,
    val platform: String?,
    val video_id: String?,
    val original_width: Int,
    val original_height: Int,
    val image_url: String

)