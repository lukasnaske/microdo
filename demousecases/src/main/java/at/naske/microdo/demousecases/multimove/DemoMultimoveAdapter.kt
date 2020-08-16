package at.naske.microdo.demousecases.multimove

import android.media.MediaPlayer
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.naske.microdo.demousecases.*
import at.naske.microdo.lib.listtolist.ListToListAdapter
import at.naske.microdo.lib.multimove.*

import at.naske.microdo.lib.swipeselect.SwipeSelectCallback
import at.naske.microdo.lib.swipeselect.SwipeSelectListener
import at.naske.microdo.lib.swipeselect.SwipeSelectHelper


class DemoMultimoveAdapter(
    mItems: MutableList<Video> = ArrayList()
) : ListToListAdapter<Video, VideoViewHolder>(mItems), SwipeSelectCallback<Video>, VideoAddable {

    val swipeSelectListener = SwipeSelectListener(this, true)
    val mSelectedItems: MutableList<MultiMoveDto<Video>> = ArrayList()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView.setOnTouchListener(swipeSelectListener)
        mRecyclerView.setOnDragListener(MultiMoveListToListOnDragListener<Video>())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.demo_multimove_swipeselect_video_view, parent, false)

        return VideoViewHolder(view, parent)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = mItems[position]
        fillData(holder, item)

        holder.itemView.setOnClickListener(swipeSelectListener)
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

        SwipeSelectHelper.showSelectedItemsCheckBox(
            holder.itemView,
            mSelectedItems.map { it.mItem }.contains(video)
        )
    }


    override fun select(item: Video) {
        val removed = mSelectedItems.removeIf { it.mItem === item }
        if (!removed) {
            mSelectedItems.add(MultiMoveDto(item, mItems.indexOf(item)))
        }
        val itemView =
            mRecyclerView.findViewHolderForAdapterPosition(mItems.indexOf(item))?.itemView
        if (removed) {
            itemView?.setOnLongClickListener(null)
        }   else {
            itemView?.setOnLongClickListener(
                MultiMoveOnLongClickListener(
                    MultiMoveDragInitializer(
                        this,
                        mSelectedItems
                    )
                )
            )
        }

        itemView?.let {
            SwipeSelectHelper.toggleCheckBox(it, !removed)
        }
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
        //NO OP
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