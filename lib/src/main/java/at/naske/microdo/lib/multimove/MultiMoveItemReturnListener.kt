package at.naske.microdo.lib.multimove

import android.view.View
import android.view.ViewGroup
import at.naske.microdo.lib.MicroDoDragEvent
import at.naske.microdo.lib.ItemDropFailedHandler
import at.naske.microdo.lib.listtolist.ListToListAdapter
import at.naske.microdo.lib.swipeselect.SwipeSelectHelper

/**
 * The item return listener is responsible to handle the on drag event in case the drag ended but no goal has been found.
 */
class MultiMoveItemReturnListener<T> : ItemDropFailedHandler {

    @Synchronized
    override fun onFailedDrop(view: View, microDoDragEvent: MicroDoDragEvent): Boolean{
        val localState = microDoDragEvent as? MultiMoveDragEvent<*>
        localState?.let {
            if (localState.dropped) {
                return false
            }
            MultiMoveHelper.hideContainedObjectcount(localState.mView)
            SwipeSelectHelper.showSelectedItemsCheckBox(localState.mView)

            val parent = getViewGroupParent(localState.mView)
            parent?.removeView(view)

            for(listToListDragEvent in localState.itemDragEvents) {
                val item = listToListDragEvent.mItem as? T

                item?.let {
                    val adapter = listToListDragEvent.mOriginalAdapter as? ListToListAdapter<T, *>
                    adapter?.let {
                        listToListDragEvent.mOriginalAdapter.addItem(listToListDragEvent.mPosition, item)
                    }
                }
            }
            localState.dropped = true
        }
        return true
    }

    private fun getViewGroupParent(view: View) : ViewGroup? {
        val parent = view.parent
        if (parent is ViewGroup) {
            return parent
        }
        return null
    }

}