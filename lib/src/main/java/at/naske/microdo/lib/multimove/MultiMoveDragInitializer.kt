package at.naske.microdo.lib.multimove

import android.view.View
import at.naske.microdo.lib.MutableRecyclerViewAdapter
import at.naske.microdo.lib.listtolist.ListToListDragEvent

/**
 * This class eases starting drag and drop with multiple elements contained. It removes all elements
 * from the original RecyclerView and initializes a {@see MultiMoveDragEvent} containing all the elements.
 * For representation the given View is used, it can be modified using the {@see MutliMoveDraggedViewCallback}
 */
open class MultiMoveDragInitializer<T>(
    val mAdapter: MutableRecyclerViewAdapter<T, *>,
    val mItemsToMove: MutableList<MultiMoveDto<T>>,
    val moveDraggedViewCallback: MultiMoveDraggedViewCallback<T>? = MultiMoveDraggedViewCallback()
){

    /**
     * Starts the multimove on all elements the object has been initialized with. The view given is used for
     * representation of the dragged objects and modified by calling the MultiMoveDraggedViewCallback
     * given.
     *
     */
    open fun startMultiMove(view: View) {
        if (mItemsToMove.isEmpty()) {
            return
        }

        mItemsToMove.forEach {
            mAdapter.removeItem(it.mItem)
        }

        moveDraggedViewCallback?.updateDraggedView(mItemsToMove, view)

        val localState = MultiMoveDragEvent(view, MultiMoveItemReturnListener<T>(), mItemsToMove.map {
            ListToListDragEvent(it.mItem, it.mOriginalPosition, mAdapter, view, MultiMoveItemReturnListener<T>())
        })

        val myShadow = View.DragShadowBuilder(view)
        view.startDragAndDrop(null, myShadow, localState, 0)
        mItemsToMove.clear()
    }
}