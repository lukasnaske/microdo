package at.naske.microdo.lib.multimove

import android.view.View
import at.naske.microdo.lib.swipeselect.SwipeSelectHelper

/**
 * Default callback to visualize multiple element that are moved with the MultiMoveDragInitializer.
 * Looks for the MultiMoveLayout in the View and shows the number of items being dropped in the
 * TextView in the MultiMoveLayout.
 */
open class MultiMoveDraggedViewCallback<T> {

    /**
     * Updates the given View to represent all items that are being dragged.
     *
     * @items The items that are being dragged.
     * @view The view being dragged and that could be updated
     */
    open fun updateDraggedView(items: MutableList<MultiMoveDto<T>>, view: View) {
        MultiMoveHelper.showContainedObjectCount(view, items.size)
        SwipeSelectHelper.hideSelectedItemsCheckBox(view)
    }
}