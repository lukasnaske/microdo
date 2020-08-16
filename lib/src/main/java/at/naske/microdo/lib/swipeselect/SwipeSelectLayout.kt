package at.naske.microdo.lib.swipeselect

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import at.naske.microdo.lib.R

/**
 * A layout showing a checkbox on the top right. It can be used to visualize that the encapsulated
 * element counts a select. Use the {@see SwipeSelectHelper} to toggle the checkboxes visibility and
 * if the checkbox counts as checked or not.
 */
class SwipeSelectLayout(
    context: Context,
    attributes: AttributeSet
) : FrameLayout(context, attributes) {

    private lateinit var contentContainer: FrameLayout

    init {
        inflate(context, R.layout.swipeselect_selected_container, this)
    }

    override fun addView(child: View) {
        if (!::contentContainer.isInitialized) {
            super.addView(child)
            val container = findViewById(R.id.selected_container_content) as? FrameLayout
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
            val container = findViewById(R.id.selected_container_content) as? FrameLayout
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
            val container = findViewById(R.id.selected_container_content) as? FrameLayout
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
            val container = findViewById(R.id.selected_container_content) as? FrameLayout
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
            val container = findViewById(R.id.selected_container_content) as? FrameLayout
            container?.let {
                contentContainer = container
            }
        } else {
            contentContainer.addView(child, index, params)
        }
    }
}