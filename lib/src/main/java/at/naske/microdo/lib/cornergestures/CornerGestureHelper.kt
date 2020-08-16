package at.naske.microdo.lib.cornergestures

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageButton
import at.naske.microdo.lib.R

/**
 * Helper class to control the corner buttons of the given view. If the given View object does not
 * have the corner gesture button components in its layout all methods do not have any effect.
 */
class CornerGestureHelper(
    view: View
) {

    var mTopLeftCorner: ImageButton? = null
    var mBottomLeftCorner: ImageButton? = null
    var mTopRightCorner: ImageButton? = null
    var mBottomRightCorner: ImageButton? = null

    init {
        mTopLeftCorner = view.findViewById(R.id.top_left_corner_button)
        mTopRightCorner = view.findViewById(R.id.top_right_corner_button)
        mBottomLeftCorner = view.findViewById(R.id.bottom_left_corner_button)
        mBottomRightCorner = view.findViewById(R.id.bottom_right_corner_button)
    }

    /**
     * Sets the visibility of all existing corner buttons to the given visiblity. See {@link View}
     * for the visibility options.
     *
     * @param visibility Either {@link View.VISIBLE}, {@link View.INVISIBLE} or {@link View.GONE}
     */
    fun setVisibilityOfAll(visibility: Int) {
        mTopLeftCorner?.visibility = visibility
        mBottomLeftCorner?.visibility = visibility
        mTopRightCorner?.visibility = visibility
        mBottomRightCorner?.visibility = visibility
    }

    /**
     * Update the bottom left corner button.
     *
     * @param color The {@Link Color} to use, default null (does not change the color of the button)
     * @param icon The id of the icon to use, default null (does not set / change the icon displayed in the button)
     * @param visibility The visiblity to set ,either {@link View.VISIBLE}, {@link View.INVISIBLE} or {@link View.GONE}, default null (does not update the visiblity)
     */
    fun setBottomLeftCorner(color: Int? = null, icon: Int? = null, visibility: Int? = null) {
        icon?.let { mBottomLeftCorner?.setImageResource(it) }
        color?.let {
            val background = mBottomLeftCorner?.background as GradientDrawable
            background.setColor(color)
        }
        visibility?.let { mBottomLeftCorner?.visibility = visibility }
    }

    /**
     * Update the bottom right corner button.
     *
     * @param color The {@Link Color} to use, default null (does not change the color of the button)
     * @param icon The id of the icon to use, default null (does not set / change the icon displayed in the button)
     * @param visibility The visiblity to set ,either {@link View.VISIBLE}, {@link View.INVISIBLE} or {@link View.GONE}, default null (does not update the visiblity)
     */
    fun setBottomRightCorner(color: Int? = null, icon: Int? = null, visibility: Int? = null) {
        icon?.let { mBottomRightCorner?.setImageResource(it) }
        color?.let {
            val background = mBottomRightCorner?.background as GradientDrawable
            background.setColor(color)
        }
        visibility?.let { mBottomRightCorner?.visibility = visibility }
    }

    /**
     * Update the top right corner button.
     *
     * @param color The {@Link Color} to use, default null (does not change the color of the button)
     * @param icon The id of the icon to use, default null (does not set / change the icon displayed in the button)
     * @param visibility The visiblity to set ,either {@link View.VISIBLE}, {@link View.INVISIBLE} or {@link View.GONE}, default null (does not update the visiblity)
     */
    fun setTopRightCorner(color: Int? = null, icon: Int? = null, visibility: Int? = null) {
        icon?.let { mTopRightCorner?.setImageResource(it) }
        color?.let {
            val background = mTopRightCorner?.background as GradientDrawable
            background.setColor(color)
        }
        visibility?.let { mTopRightCorner?.visibility = visibility }
    }

    /**
     * Update the top left corner button.
     *
     * @param color The {@Link Color} to use, default null (does not change the color of the button)
     * @param icon The id of the icon to use, default null (does not set / change the icon displayed in the button)
     * @param visibility The visiblity to set ,either {@link View.VISIBLE}, {@link View.INVISIBLE} or {@link View.GONE}, default null (does not update the visiblity)
     */
    fun setTopLeftCorner(color: Int? = null, icon: Int? = null, visibility: Int? = null) {
        icon?.let { mTopLeftCorner?.setImageResource(it) }
        color?.let {
            val background = mTopLeftCorner?.background as GradientDrawable
            background.setColor(color)
        }
        visibility?.let { mTopLeftCorner?.visibility = visibility }
    }

}