package at.naske.microdo.demousecases

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.provider.MediaStore
import at.naske.microdo.lib.tagging.VideoTaggingService
import java.io.File
import java.util.*

data class Video (
    val file: File,
    val thumbnail: Bitmap? = ThumbnailUtils.createVideoThumbnail(
        file.absolutePath,
        MediaStore.Video.Thumbnails.MINI_KIND)
) {
    val id = UUID.randomUUID().toString()
    val mTaggingService = VideoTaggingService(file)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Video

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}