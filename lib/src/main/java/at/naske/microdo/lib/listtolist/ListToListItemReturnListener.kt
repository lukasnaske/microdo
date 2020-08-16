package at.naske.microdo.lib.listtolist

import android.view.View
import android.view.ViewGroup
import at.naske.microdo.lib.MicroDoDragEvent
import at.naske.microdo.lib.ItemDropFailedHandler

/**
 * The item return listener is responsible to handle the on drag event in case the drag ended but no goal has been found.
 */
open class ListToListItemReturnListener<T> : ItemDropFailedHandler {

    @Synchronized
    override fun onFailedDrop(view: View, microDoDragEvent: MicroDoDragEvent): Boolean{
        val localState = microDoDragEvent as? ListToListDragEvent<T>
        localState?.let {
            if (localState.dropped) {
                return false
            }
            val parent = getViewGroupParent(localState.mView)
            parent?.removeView(view)

            localState.mOriginalAdapter.addItem(localState.mPosition, localState.mItem)
            localState.dropped = true
        }
        return true
    }

    open fun getViewGroupParent(view: View) : ViewGroup? {
        val parent = view.parent
        if (parent is ViewGroup) {
            return parent
        }
        return null
    }

}