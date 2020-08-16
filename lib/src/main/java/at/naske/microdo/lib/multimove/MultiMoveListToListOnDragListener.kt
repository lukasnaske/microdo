package at.naske.microdo.lib.multimove

import android.view.DragEvent
import android.view.View
import at.naske.microdo.lib.listtolist.ListToListOnDragListener
import at.naske.microdo.lib.swipeselect.SwipeSelectHelper

/**
 * Extension of the ListToListOnDragListener making it possible to handle MultiMoveDragEvent and
 * add all of the dropped elements to the attached View.
 */
class MultiMoveListToListOnDragListener<T> : ListToListOnDragListener<T>() {

    override fun handleDrop(view: View, event: DragEvent): Boolean {
        val target = getListToListDropable(view)
        target?.let {

            val localState = event.localState as? MultiMoveDragEvent<*>
            localState?.let {
                removeFromParent(localState.mView)

                localState.mView.visibility = View.VISIBLE

                MultiMoveHelper.hideContainedObjectcount(localState.mView)
                SwipeSelectHelper.showSelectedItemsCheckBox(localState.mView)

                var position = -1
                for (listToListDragEvent in localState.itemDragEvents) {
                    val item = listToListDragEvent.mItem as? T
                    if (position < 0) {
                        position = target.addItem(item, event.x, event.y)
                    } else {
                        target.addItem(++position, item)
                    }
                }
                return true
            }
        }
        return false
    }
}