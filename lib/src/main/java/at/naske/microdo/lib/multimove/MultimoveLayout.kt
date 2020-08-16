package at.naske.microdo.lib.multimove

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import at.naske.microdo.lib.R

/**
 * The MultiMovelayout can be used to represent multiple elements being contained in a single View.
 * It has a textView in the top right of the View.
 */
class MultimoveLayout(
    context: Context,
    attributes: AttributeSet
) : FrameLayout(context, attributes) {

    private lateinit var contentContainer: FrameLayout

    init {
        inflate(context, R.layout.multimove_container, this)
    }

    override fun addView(child: View) {
        if (!::contentContainer.isInitialized) {
            super.addView(child)
            val container = findViewById(R.id.multimove_container_content) as? FrameLayout
            container?.let {
                contentContainer = container
            }
        } else {
            contentContainer.addView(child)
        }
    }

    override fun addView(child: View, index: Int) {
        if (!::contentContainer.isInitialized) {
            super.addView(child, index)
            val container = findViewById(R.id.multimove_container_content) as? FrameLayout
            container?.let {
                contentContainer = container
            }
        } else {
            contentContainer.addView(child, index)
        }
    }

    override fun addView(child: View, width: Int, height: Int) {
        if (!::contentContainer.isInitialized) {
            super.addView(child, width, height)
            val container = findViewById(R.id.multimove_container_content) as? FrameLayout
            container?.let {
                contentContainer = container
            }
        } else {
            contentContainer.addView(child, width, height)
        }
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams?) {
        if (!::contentContainer.isInitialized) {
            super.addView(child, params)
            val container = findViewById(R.id.multimove_container_content) as? FrameLayout
            container?.let {
                contentContainer = container
            }
        } else {
            contentContainer.addView(child, params)
        }

    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        if (!::contentContainer.isInitialized) {
            super.addView(child, index, params)
            val container = findViewById(R.id.multimove_container_content) as? FrameLayout
            container?.let {
                contentContainer = container
            }
        } else {
            contentContainer.addView(child, index, params)
        }
    }
}