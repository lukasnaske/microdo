package at.naske.microdo.lib.tagging

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import at.naske.microdo.lib.R

/**
 * The TaggingLayout can be used to represent that the element encapsulated in the View was tagged.
 * Each side can be toggled on and off and by choosing different color it can represent different tags.
 * Use the TaggingHelper to toggle the sides visibility and color.
 *
 */
class SideTagsLayout(
    context: Context,
    attributes: AttributeSet
) : ConstraintLayout(context, attributes) {

    private lateinit var contentContainer: ConstraintLayout

    private val layoutIncludedViews = listOf(
        R.id.tagged_content,
        R.id.tagging_constraint_layout_container,
        R.id.bottom_tag,
        R.id.left_tag,
        R.id.right_tag,
        R.id.top_tag,
        this.id
    )

    init {
        inflate(context, R.layout.tagging_tagged_item_container, this)
        contentContainer = findViewById(R.id.tagged_content)
    }

    override fun addView(child: View) {
        if (!this::contentContainer.isInitialized) {
            super.addView(child)
        } else {
            contentContainer.addView(child)
        }
    }

    override fun addView(child: View, index: Int) {
        if (this::contentContainer.isInitialized) {
            super.addView(child, index)
        } else {
            contentContainer.addView(child, index)
        }
    }

    override fun addView(child: View, width: Int, height: Int) {
        if (!this::contentContainer.isInitialized) {
            super.addView(child, width, height)
        } else {
            contentContainer.addView(child, width, height)
        }
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams?) {
        if (!this::contentContainer.isInitialized) {
            super.addView(child, params)
        } else {
            contentContainer.addView(child, params)
        }

    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        if (!this::contentContainer.isInitialized) {
            super.addView(child, index, params)
        } else {
            contentContainer.addView(child, index, params)
        }
    }


}