package pt.boldint.carbon.boldhunter.data.model

import android.net.Uri

data class Media(
        val id: Int,
        val media_type: String,
        val original_width: Int,
        val original_height: Int,
        val image_url: Uri,
        val platform: String?,
        val video_id: String?

)