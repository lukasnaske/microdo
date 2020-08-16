package at.naske.microdo.demousecases.listtolist

import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.naske.microdo.demousecases.*
import at.naske.microdo.lib.listtolist.ListToListAdapter

import at.naske.microdo.lib.swipeselect.SwipeSelectCallback
import at.naske.microdo.lib.swipeselect.SwipeSelectListener
import at.naske.microdo.lib.tagging.TaggingHelper


class DemoListToListRecyclerViewAdapter(
    mItems: MutableList<Video> = ArrayList()
) : ListToListAdapter<Video, VideoViewHolder>(mItems), SwipeSelectCallback<Video>, VideoAddable {

    val swipeSelectOnTouchListener = SwipeSelectListener(this, true)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        mRecyclerView.setOnTouchListener(swipeSelectOnTouchListener)
        mRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        mRecyclerView.isNestedScrollingEnabled = false
    }

    override fun select(item: Video) {

    }

    override fun getItemBelow(x: Float, y: Float): Video? {
        val position = super.getPosition(x, y)

        return if (position >= 0 && position < mItems.size) {
            mItems[position]
        } else {
            null
        }
    }

    override fun swipeEnded(selectedItems: Collection<Video>) {
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutToLoad = if (mRecyclerView.layoutManager!!.canScrollVertically()) R.layout.demo_video_view_vertical else R.layout.demo_video_view_horizontal
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutToLoad, parent, false)

        return VideoViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = mItems[position]
        fillData(holder, item)
        super.onBindViewHolder(holder, position)
    }

    fun fillData(holder: VideoViewHolder, video: Video) {
        val file = video.file
        with(holder) {

            mVideoPreview.setImageBitmap(video.thumbnail)

            setTagVisibility(holder, video)

            val parentView = parent as View
            val itemSquareLength = if (parentView.measuredHeight > parentView.measuredWidth) parentView.measuredWidth / 2 else parentView.measuredHeight / 2

            itemView.layoutParams.width = itemSquareLength
            itemView.layoutParams.height = itemSquareLength

            mVideoView.setVideoURI(Uri.parse(file.absolutePath))
            mVideoView.setOnPreparedListener {
                it.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
            }
            mVideoView.setOnCompletionListener {
                mVideoView.stopPlayback()
                mVideoPreview.visibility = View.VISIBLE
                mVideoView.visibility = View.GONE
            }

            itemView.setOnClickListener {
                if (mVideoView.isPlaying) {
                    mVideoPreview.visibility = View.VISIBLE
                    mVideoView.visibility = View.INVISIBLE
                    mVideoView.stopPlayback()
                } else {
                    mVideoPreview.visibility = View.INVISIBLE
                    mVideoView.visibility = View.VISIBLE
                    mVideoView.start()
                }
            }
        }
    }

    fun setTagVisibility(holder: VideoViewHolder?, video: Video?) {
        video?.let {
            holder?.let {
                with(holder) {
                    val red = video.mTaggingService.getTag("red")
                    val blue = video.mTaggingService.getTag("blue")
                    val yellow = video.mTaggingService.getTag("yellow")
                    val green = video.mTaggingService.getTag("green")
                    val taggingHelper = TaggingHelper(holder.itemView)

                    red.value?.let {
                        taggingHelper.toggleBottomTag(if (it.toBoolean()) View.VISIBLE else View.GONE, Color.RED)
                    }

                    blue.value?.let {
                        taggingHelper.toggleTopTag(if (it.toBoolean()) View.VISIBLE else View.GONE, Color.BLUE)
                    }

                    yellow.value?.let {
                        taggingHelper.toggleLeftTag(if (it.toBoolean()) View.VISIBLE else View.GONE, Color.YELLOW)
                    }

                    green.value?.let {
                        taggingHelper.toggleRightTag(if (it.toBoolean()) View.VISIBLE else View.GONE, Color.GREEN)

                    }
                }
            }
        }
    }

    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
        with(holder) {
            mVideoView.stopPlayback()
            mVideoPreview.visibility = View.VISIBLE
            mVideoView.visibility = View.INVISIBLE
        }
    }

    override fun getItemIn(view: View): Video? {
        val position = mRecyclerView.getChildAdapterPosition(view)
        return if (position >= 0) mItems[position] else null
    }

    override fun addVideo(video: Video) {
        mRecyclerView.post {
            addItem(video)
        }
    }
}

