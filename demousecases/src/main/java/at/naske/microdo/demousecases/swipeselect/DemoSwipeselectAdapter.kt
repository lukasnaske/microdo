package at.naske.microdo.demousecases.swipeselect

import android.media.MediaPlayer
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import at.naske.microdo.demousecases.*

import at.naske.microdo.lib.swipeselect.SwipeSelectCallback
import at.naske.microdo.lib.swipeselect.SwipeSelectListener
import at.naske.microdo.lib.swipeselect.SwipeSelectHelper


class DemoSwipeselectAdapter(
    val mItems: MutableList<Video> = ArrayList()
) : RecyclerView.Adapter<VideoViewHolder>(), SwipeSelectCallback<Video>, VideoAddable {

    val swipeSelectOnTouchListener = SwipeSelectListener(this, true)
    lateinit var mRecyclerView : RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
        mRecyclerView.setOnTouchListener(swipeSelectOnTouchListener)
    }

    override fun select(item: Video) {
        mRecyclerView.findViewHolderForAdapterPosition(mItems.indexOf(item))?.itemView?.let {
            SwipeSelectHelper.toggleCheckBox(it)
        }
    }

    override fun getItemBelow(x: Float, y: Float): Video? {
        val view = mRecyclerView.findChildViewUnder(x, y)
        var position = -1
        view?.let {
            val viewHolder = mRecyclerView.findContainingViewHolder(view)
            position = mRecyclerView.getChildAdapterPosition(view)
        }

        return if (position >= 0 && position < mItems.size) {
            mItems[position]
        } else {
            null
        }
    }

    override fun swipeEnded(selectedItems: Collection<Video>) {
        //NO OP
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.demo_swipeselect_video_view, parent, false)

        return VideoViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = mItems[position]
        fillData(holder, item)
    }

    fun fillData(holder: VideoViewHolder, video: Video) {
        val file = video.file
        with(holder) {
            mVideoPreview.setImageBitmap(video.thumbnail)

            val parentView = parent as View
            val itemSquareLength =
                if (parentView.measuredHeight > parentView.measuredWidth) parentView.measuredWidth / 2 else parentView.measuredHeight / 2

            itemView.layoutParams.width = itemSquareLength
            itemView.layoutParams.height = itemSquareLength

            mVideoView.setVideoURI(Uri.parse(file.absolutePath))
            mVideoView.requestFocus()
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

        val checkBox = holder.itemView.findViewById(R.id.swipeselect_checkbox) as? CheckBox
        checkBox?.let {
            checkBox.isChecked = swipeSelectOnTouchListener.selectedItemsList.contains(video)
        }
    }

    override fun getItemIn(view: View): Video? {
        val position = mRecyclerView.getChildAdapterPosition(view)
        return if (position >= 0) mItems[position] else null
    }

    override fun addVideo(video: Video) {
        mRecyclerView.post {
            mItems.add(video)
            notifyItemInserted(mItems.size - 1)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}

