package at.naske.microdo.lib.cornergestures

import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.ACTION_UP
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import java.lang.IllegalArgumentException

/**
 * The CornerGesturesOnTouchListener handles swipes within a view to notify that a swipe to one of the corners of a
 * view. It can be customised at which percentage <= 50% of the smaller side of the view the activation should start.
 * The callback is also notified about hovers over one of the corners.
 */
class CornerGesturesOnTouchListener(
    private val mActivationPercentage: Double,
    private val mGesturesListener: CornerGesturesListener
) : View.OnTouchListener {

    var swipeActivated = false

    init {
        if (mActivationPercentage > 0.5) {
            throw IllegalArgumentException("The activation percentage has to be below or equal to 0.5")
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            ACTION_DOWN -> {
                val x1 = event.x
                val y1 = event.y
                swipeActivated = isLocationOutsideOfActivationDistance(v, x1, y1)
                return true
            }
            ACTION_MOVE -> {
                if (!swipeActivated) {
                    return false
                }

                val x2 = event.x
                val y2 = event.y

                mGesturesListener.onHover(getCorner(v, x2, y2))
                return true
            }
            ACTION_UP -> {
                if (!swipeActivated) {
                    return false
                }
                val x2 = event.x
                val y2 = event.y

                mGesturesListener.onSwipeTo(getCorner(v, x2, y2))
                return true
            }
        }
        return false
    }

    /**
     * Calculates the corner the given location is closest to and returns the corner if the location is within the
     * activation distance.
     */
    private fun getCorner(v: View, x2: Float, y2: Float): Corner? {
        val width = v.measuredWidth
        val height = v.measuredHeight

        val activationDistance = getActivationDistance(v)

        val distanceTopLeft = Math.hypot((0 - x2).toDouble(), (0 - y2).toDouble())
        val distanceTopRight = Math.hypot((width - x2).toDouble(), (0 - y2).toDouble())
        val distanceBottomLeft = Math.hypot((0 - x2).toDouble(), (height - y2).toDouble())
        val distanceBottomRight = Math.hypot((width - x2).toDouble(), (height - y2).toDouble())

        if (distanceTopLeft <= activationDistance &&
            distanceTopLeft <= distanceTopRight &&
            distanceTopLeft <= distanceBottomLeft &&
            distanceTopLeft <= distanceBottomRight) {
            return Corner.TOP_LEFT
        } else if (distanceTopRight <= activationDistance
            && distanceTopRight <= distanceBottomLeft
            && distanceTopRight <= distanceBottomRight) {
            return Corner.TOP_RIGHT
        } else if (distanceBottomLeft <= activationDistance &&
            distanceBottomLeft <= distanceBottomRight) {
            return Corner.BOTTOM_LEFT
        } else if (distanceBottomRight <= activationDistance) {
            return Corner.BOTTOM_RIGHT
        } else {
            return null
        }
    }

    /**
     * Checks if the given location is outside of the activation distance.
     */
    private fun isLocationOutsideOfActivationDistance(v: View, x1: Float, y1: Float) : Boolean {
        val activationDistance = getActivationDistance(v)

        val width = v.measuredWidth
        val height = v.measuredHeight

        val distanceTopLeft = Math.hypot((0 - x1).toDouble(), (0 - y1).toDouble())
        val distanceTopRight = Math.hypot((width - x1).toDouble(), (0 - y1).toDouble())
        val distanceBottomLeft = Math.hypot((0 - x1).toDouble(), (height - y1).toDouble())
        val distanceBottomRight = Math.hypot((width - x1).toDouble(), (height - y1).toDouble())

        return distanceTopLeft > activationDistance &&
                distanceBottomLeft > activationDistance &&
                distanceTopRight > activationDistance &&
                distanceBottomRight > activationDistance
    }

    /**
     * Calculates the activation distance for the given view by taking the shorter end of the given view and multiplying
     * it by the activation percentage.
     */
    private fun getActivationDistance(v: View) : Double {
        return if (v.measuredHeight > v.measuredWidth) v.measuredWidth * mActivationPercentage else v.measuredHeight * mActivationPercentage
    }

    /**
     * Enum to represent the four corners of a view
     */
    enum class Corner{
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

}