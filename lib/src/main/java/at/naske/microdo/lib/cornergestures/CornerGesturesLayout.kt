package at.naske.microdo.lib.cornergestures

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import at.naske.microdo.lib.R

/**
 * Custom layout for the corner gestures. When used the child views added within the layout are
 * overlayed with corner buttons. Use {@see CornerGestureHelper} to control the corner gesture buttons.
 */
class CornerGesturesLayout(
    context: Context,
    attributes: AttributeSet
) : FrameLayout(context, attributes) {

    private lateinit var contentContainer: FrameLayout

    init {
        inflate(context, R.layout.cg_container, this)
        contentContainer = findViewById(R.id.cg_content_container)
    }

    override fun addView(child: View) {
        if (!::contentContainer.isInitialized) {
            super.addView(child)
        } else {
            contentContainer.addView(child)
        }
    }

    override fun addView(child: View, index: Int) {
        if (!::contentContainer.isInitialized) {
            super.addView(child, index)
        } else {
            contentContainer.addView(child, index)
        }
    }

    override fun addView(child: View, width: Int, height: Int) {
        if (!::contentContainer.isInitialized) {
            super.addView(child, width, height)
        } else {
            contentContainer.addView(child, width, height)
        }
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams?) {
        if (!::contentContainer.isInitialized) {
            super.addView(child, params)
        } else {
            contentContainer.addView(child, params)
        }

    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        if (!::contentContainer.isInitialized) {
            super.addView(child, index, params)
        } else {
            contentContainer.addView(child, index, params)
        }
    }
}