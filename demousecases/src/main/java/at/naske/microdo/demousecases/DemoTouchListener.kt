package at.naske.microdo.demousecases

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class DemoTouchListener(
    context: Context,
    val demoGestureDetectorListener: DemoGestureDetectorListener? = null,
    val onTouchListener: View.OnTouchListener? = null
) : View.OnTouchListener{

    val gestureDetector = GestureDetector(context, demoGestureDetectorListener)

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var handled = false
        if (demoGestureDetectorListener != null) {
            handled = gestureDetector.onTouchEvent(event)
        }
        if (!handled) {
            if (onTouchListener != null) {
                return onTouchListener.onTouch(v, event)
            }
        }
        return handled

    }

}