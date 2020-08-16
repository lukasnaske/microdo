package at.naske.microdo.demousecases

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.res.Resources
import android.media.ThumbnailUtils
import android.os.AsyncTask
import android.os.CancellationSignal
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import java.io.File

class VideoReader(
    val mPath: String,
    val videoAddable: VideoAddable? = null
) : AsyncTask<Void, Void, Unit>() {

    init {
        videoAddable?.let {
            addVideoAddable(videoAddable)
        }
    }

    companion object {
        val videoAddables: MutableList<VideoAddable> = ArrayList()
        val videos: MutableList<Video> = ArrayList()

        fun addVideoAddable(videoAddable: VideoAddable) {
            videoAddables.add(videoAddable)
            videos.forEach {
                videoAddable.addVideo(it)
            }
        }
    }

    override fun doInBackground(vararg params: Void?) {
        val fileDirectory = File(Environment.getExternalStorageDirectory(), mPath)

        if (fileDirectory.isDirectory) {
            val filesInDirectory = fileDirectory.listFiles()
            
            for (i in 0 until filesInDirectory.size) {
                if (isCancelled) {
                    break
                }
                val file = filesInDirectory[i]
                if (file.name.endsWith(".mp4")) {
                    val video = Video(file)
                    videoAddables.forEach {
                        it.addVideo(video)
                    }
                    videos.add(video)
                }
            }
        }
    }

}