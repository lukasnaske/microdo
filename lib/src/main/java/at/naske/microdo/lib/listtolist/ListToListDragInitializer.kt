package at.naske.microdo.lib.listtolist

import android.view.View

/**
 * Initializes the drag and drop behaviour for the List-To-List behaviour. It removes the item
 * from the orignal adapter and starts drag and drop using the dragged View as representation.
 * It also initializes the {@see ListToListDragEvent} with the correct parameters.
 */
open class ListToListDragInitializer<T>(
    val mListToListAdapter: ListToListAdapter<T, *>
) {

    /**
     * Starts the drag and drop with the correct drag event to be recognized by the event listeners.
     *
     * @param item The item to be draged, which is contained in the given View
     * @param viewToDrag The View to use to display during the drag and drop
     */
    open fun startDrag(item: T, viewToDrag: View){
        val viewShadow = View.DragShadowBuilder(viewToDrag)

        val position = mListToListAdapter.removeItem(item)
        val listToListLocalState = ListToListDragEvent(
            item, position, mListToListAdapter, viewToDrag, ListToListItemReturnListener<T>()
        )
        viewToDrag.startDragAndDrop(null, viewShadow, listToListLocalState, 0)
    }
}