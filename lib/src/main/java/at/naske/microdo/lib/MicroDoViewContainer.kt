package at.naske.microdo.lib

import android.view.ViewGroup

data class MicroDoViewContainer(
    /**
     * The actual container view. Root element of this contianer.
     */
    val mContainer: ViewGroup,
    /**
     * The predefined container for the content that should be displayed within. Add child views to
     * this one to ensure proper visualization.
     */
    val mContentContainer: ViewGroup
)