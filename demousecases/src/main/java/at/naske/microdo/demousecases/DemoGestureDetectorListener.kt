package at.naske.microdo.demousecases

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import at.naske.microdo.lib.multimove.MultiMoveOnLongClickListener

class DemoGestureDetectorListener(
    val view: View,
    val onLongClickListener: View.OnLongClickListener? = null,
    val onClickListener: View.OnClickListener? = null
): GestureDetector.SimpleOnGestureListener() {
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        onClickListener?.onClick(view)
        return onClickListener != null
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        onLongClickListener?.onLongClick(view)
    }


}