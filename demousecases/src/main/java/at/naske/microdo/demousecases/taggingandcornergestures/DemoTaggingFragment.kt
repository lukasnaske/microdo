package at.naske.microdo.demousecases.taggingandcornergestures

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.naske.microdo.demousecases.R
import at.naske.microdo.demousecases.VideoReader
import at.naske.microdo.lib.cornergestures.CornerGestureHelper
import at.naske.microdo.lib.cornergestures.CornerGesturesListener
import at.naske.microdo.lib.cornergestures.CornerGesturesOnTouchListener
import at.naske.microdo.lib.tagging.Tag
import kotlinx.android.synthetic.main.demo_fragment_cg_tagging_view.view.*


class DemoTaggingFragment : Fragment(), CornerGesturesListener {

    private lateinit var mAdapter: DemoTaggingListAdapter
    lateinit var mCornerGestureHelper: CornerGestureHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.demo_fragment_cg_tagging_view, container, false)

        mCornerGestureHelper = CornerGestureHelper(view)
        mCornerGestureHelper.setVisibilityOfAll(View.GONE)
        mCornerGestureHelper.setBottomLeftCorner(icon = android.R.drawable.star_big_on)
        mCornerGestureHelper.setBottomRightCorner(icon = android.R.drawable.ic_delete)
        mCornerGestureHelper.setTopRightCorner(icon = android.R.drawable.ic_lock_silent_mode_off)
        mCornerGestureHelper.setTopLeftCorner(icon = android.R.drawable.presence_busy)

        val videoList = view.video_list as RecyclerView
        videoList.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        val adapter =
            DemoTaggingListAdapter(view)
        mAdapter = adapter

        view.cg_frame.setOnTouchListener(CornerGesturesOnTouchListener(0.3, this))
        videoList.adapter = adapter

        VideoReader.addVideoAddable(mAdapter)
        return view
    }


    override fun onHover(corner: CornerGesturesOnTouchListener.Corner?) {
        setHoverActiveColor()
        when (corner) {
            CornerGesturesOnTouchListener.Corner.TOP_LEFT -> {
                mCornerGestureHelper.setTopLeftCorner(color = Color.BLUE)
            }
            CornerGesturesOnTouchListener.Corner.TOP_RIGHT -> {
                mCornerGestureHelper.setTopRightCorner(color = Color.GREEN)
            }
            CornerGesturesOnTouchListener.Corner.BOTTOM_LEFT -> {
                mCornerGestureHelper.setBottomLeftCorner(color = Color.YELLOW)
            }
            CornerGesturesOnTouchListener.Corner.BOTTOM_RIGHT -> {
                mCornerGestureHelper.setBottomRightCorner(color = Color.RED)
            }
        }
    }

    private fun setHoverActiveColor() {
        mCornerGestureHelper.setVisibilityOfAll(View.VISIBLE)
        mCornerGestureHelper.setTopLeftCorner(color = ColorUtils.setAlphaComponent(Color.BLUE, 80))
        mCornerGestureHelper.setBottomLeftCorner(color = ColorUtils.setAlphaComponent(Color.YELLOW, 80))
        mCornerGestureHelper.setTopRightCorner(color = ColorUtils.setAlphaComponent(Color.GREEN, 80))
        mCornerGestureHelper.setBottomRightCorner(color = ColorUtils.setAlphaComponent(Color.RED, 80))
    }

    private fun resetOnHover() {
        mCornerGestureHelper.setVisibilityOfAll(View.GONE)
    }

    override fun onSwipeTo(corner: CornerGesturesOnTouchListener.Corner?) {
        var key = "";
        when (corner) {
            CornerGesturesOnTouchListener.Corner.TOP_LEFT -> {
                key = "blue"
            }
            CornerGesturesOnTouchListener.Corner.TOP_RIGHT -> {
                key = "green"
            }
            CornerGesturesOnTouchListener.Corner.BOTTOM_LEFT -> {
                key = "yellow"
            }
            CornerGesturesOnTouchListener.Corner.BOTTOM_RIGHT -> {
                key = "red"
            }
        }
        val tag = mAdapter.mCurrentSelected?.mTaggingService?.getTag(key)
        var activate = true
        tag?.value?.let {
            activate = !it.toBoolean()
        }

        mAdapter.tagCurrent(Tag(key, activate.toString()))
        resetOnHover()
    }


}
