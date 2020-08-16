package at.naske.microdo.demousecases.alternatinglist

import android.media.MediaPlayer
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.naske.microdo.demousecases.*


class DemoAlternatingListRecyclerViewAdapter(
    val mItems: MutableList<Video> = ArrayList()
) : RecyclerView.Adapter<VideoViewHolder>(), VideoAddable {

    var mVertical = false
    lateinit var mRecyclerView: RecyclerView;

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.demo_video_view_vertical, parent, false)

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

            mVideoView.setVideoURI(Uri.parse(file.absolutePath))
            mVideoView.requestFocus()
            mVideoView.setOnPreparedListener {
                it.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
            }
            mVideoView.setOnCompletionListener {
                mVideoView.stopPlayback()
                mVideoPreview.visibility = View.VISIBLE
                mVideoView.visibility = View.INVISIBLE
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

    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
        with(holder) {
            mVideoView.stopPlayback()
            mVideoPreview.visibility = View.VISIBLE
            mVideoView.visibility = View.INVISIBLE
        }
    }

    override fun addVideo(video: Video) {
        mRecyclerView.post {
            mItems.add(video)
            notifyItemInserted(mItems.size - 1)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size;
    }
}

