package at.naske.microdo.demousecases.taggingandcornergestures

import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import at.naske.microdo.demousecases.*
import at.naske.microdo.lib.tagging.Tag
import at.naske.microdo.lib.tagging.TaggingHelper
import kotlinx.android.synthetic.main.demo_fragment_cg_tagging_view.view.*

class DemoTaggingListAdapter(
    val mTaggingView: View
) : RecyclerView.Adapter<ImageViewHolder>(), VideoAddable {

    val mItems = ArrayList<Video>()
    var mCurrentSelected: Video? = null
    lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.demo_tagged_video_view_vertical, parent, false)

        return ImageViewHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        fillData(holder, mItems[position])
    }

    override fun addVideo(video: Video) {
        mRecyclerView.post {
            mItems.add(video)
            notifyItemInserted(mItems.size-1)
            if (mItems.size == 1) {
                startVideo(video)
            }
        }
    }
    fun fillData(holder: ImageViewHolder, video: Video) {
        with(holder) {
            mVideoPreview.setImageBitmap(video.thumbnail)
            mVideoPreview.scaleType = ImageView.ScaleType.FIT_CENTER
            mVideoPreview.visibility = View.VISIBLE

            holder.itemView.setOnClickListener {
                startVideo(video)
            }
            setTagVisibility(holder.itemView, video)
        }
    }

    fun tagCurrent(tag: Tag) {
        mCurrentSelected?.let {
            val video = it
            video.mTaggingService.tagFile(tag)
            val viewHolder = mRecyclerView.findViewHolderForAdapterPosition(mItems.indexOf(video))
            setTagVisibility(viewHolder?.itemView, mCurrentSelected)
            setTagVisibility(mTaggingView, mCurrentSelected)
        }
    }

    fun startVideo(video: Video) {
        val playVideoView = mTaggingView.play_video
        playVideoView.stopPlayback()
        playVideoView.setVideoURI(Uri.parse(video.file.absolutePath))
        playVideoView.requestFocus()
        playVideoView.setOnPreparedListener { mediaPlayer: MediaPlayer ->
            mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
            mediaPlayer.isLooping = true
        }
        playVideoView.setOnCompletionListener { mediaPlayer: MediaPlayer ->
            mediaPlayer.reset()
        }

        playVideoView.seekTo(1)
        playVideoView.start()
        setTagVisibility(mTaggingView, video)
        mCurrentSelected = video
    }

    fun setTagVisibility(view: View?, video: Video?) {
        video?.let {
            view?.let {
                val taggingHelper = TaggingHelper(view)
                val red = video.mTaggingService.getTag("red")
                val blue = video.mTaggingService.getTag("blue")
                val yellow = video.mTaggingService.getTag("yellow")
                val green = video.mTaggingService.getTag("green")


                taggingHelper.toggleBottomTag(getVisibility(red.value), Color.RED)

                taggingHelper.toggleTopTag(getVisibility(blue.value), Color.BLUE)

                taggingHelper.toggleLeftTag(getVisibility(yellow.value), Color.YELLOW)

                taggingHelper.toggleRightTag(getVisibility(green.value), Color.GREEN)
            }
        }
    }

    private fun getVisibility(visible: String?) : Int {
        return if (visible == null || !visible.toBoolean()) View.GONE else View.VISIBLE
    }
}
