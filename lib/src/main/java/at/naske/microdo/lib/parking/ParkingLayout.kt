package at.naske.microdo.lib.parking

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import at.naske.microdo.lib.MicroDoDragEvent
import at.naske.microdo.lib.R

/**
 * The ParkingLayout can be used to drop any View into that was dropped using a MicroDoDragEvent. Use
 * scaleValueX and scaleValueY in the layout to define if and how much the dropped Views should be scaled.
 */
open class ParkingLayout(
    context: Context,
    attributes: AttributeSet
) : RelativeLayout(context, attributes), Parkable {

    val mScaleValueX : Float
    val mScaleValueY : Float

    init {
        this.setOnDragListener(ParkingOnDragListener(this))
        context.theme.obtainStyledAttributes(
            attributes,
            R.styleable.ParkingLayout,
            0, 0).apply {

            try {
                mScaleValueX = getFloat(R.styleable.ParkingLayout_scaleValueX, 0.5f)
                mScaleValueY = getFloat(R.styleable.ParkingLayout_scaleValueY, 0.5f)
            } finally {
                recycle()
            }
        }
    }

    override fun addParkedView(viewToMove: View, x: Float, y: Float, originalEvent: MicroDoDragEvent?) {
        val view = addToContainer(viewToMove, x, y)
        view.setOnLongClickListener(ParkedItemOnLongClickListener(originalEvent))
    }

    open fun addToContainer(viewToMove: View,x: Float, y: Float) : View{
        val viewCopy = ViewCopy(this.context, viewToMove)
        this.addView(viewCopy)

        viewCopy.visibility = View.VISIBLE

        val width = viewCopy.layoutParams.width
        val height = viewCopy.layoutParams.height
        val xToMoveTo =
            x - (width.toFloat()  / 2)
        val yToMoveTo =
            y - (height.toFloat()  / 2)
        viewCopy.translationX = xToMoveTo
        viewCopy.translationY = yToMoveTo

        viewCopy.scaleX = mScaleValueX
        viewCopy.scaleY = mScaleValueY


        return viewCopy
    }
}