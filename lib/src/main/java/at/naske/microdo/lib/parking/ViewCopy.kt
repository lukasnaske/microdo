package at.naske.microdo.lib.parking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup

/**
 * Creates a copy of the representation of the given View. Only the visual representation is added
 * by drawing it on a Canvas, everything else is not copied from the View.
 */
class ViewCopy(
    context: Context,
    viewToCopy: View
) : View(context) {

    val mBitmap: Bitmap

    init {
        layoutParams = ViewGroup.LayoutParams(viewToCopy.width,viewToCopy.height)
        mBitmap = Bitmap.createBitmap(layoutParams.width,layoutParams.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(mBitmap)
        viewToCopy.draw(canvas)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap, 0.0f, 0.0f, null)
    }
}