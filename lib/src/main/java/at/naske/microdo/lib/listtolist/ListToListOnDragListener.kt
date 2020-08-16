package at.naske.microdo.lib.listtolist

import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.naske.microdo.lib.MicroDoDragEvent

/**
 * On drag listener that can accept an item and add it to the view it is attached to. The view
 * it is attached to has to be a ListToListDropable or a RecyclerView with and Adapter that is a
 * ListToListDropable.
 */
open class ListToListOnDragListener<T> : View.OnDragListener {

    open override fun onDrag(view: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                handleDrop(view, event)
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                if (event.result) {
                    return true
                }
                val localState = event.localState as? MicroDoDragEvent
                localState?.let {
                    return localState.onNotDroppedListener.onFailedDrop(view, localState)
                }
            }
            DragEvent.ACTION_DRAG_STARTED -> {
                return true
            }
        }
        return true
    }

    /**
     * Handles the {@see DrageEvent.ACTION_DROP}. It adds the dropped item to the View the
     * ListToListOnDragListener is attached to if it contains a ListToListDragEvent and the elemnt
     * dropped within has the correct type.
     */
    open fun handleDrop(view: View, event: DragEvent): Boolean {
        val target = getListToListDropable(view)
        target?.let {
            val localState = event.localState as? ListToListDragEvent<*>
            localState?.let {
                removeFromParent(localState.mView)

                localState.mView.visibility = View.VISIBLE

                val item = localState.mItem as? T

                target.addItem(item, event.x, event.y)

                return true
            }
        }
        return false
    }

    /**
     * Ensures the view has been removed from its parent
     */
    open fun removeFromParent(view: View) {
        val parent = getViewGroupParent(view)
        parent?.removeView(view)
    }

    /**
     * Returns the parent of the view as a ViewGroup, null otherwise
     */
    open fun getViewGroupParent(view: View) : ViewGroup? {
        val parent = view.parent
        if (parent is ViewGroup) {
            return parent
        }
        return null
    }

    /**
     * Returns the given View or its parent as RecyclerView, null otherwise
     */
    open fun getRecyclerView(view: View): RecyclerView? {
        if (view is RecyclerView) {
            return view
        }
        val parent = view.parent
        if (parent is RecyclerView) {
            return parent
        }
        return null
    }

    /**
     * Returns the given View, or if it is a RecyclerView its Adapter cast to a ListToListDroppable
     * of the same type. {@code null} otherwise.
     */
    open fun getListToListDropable(view: View): ListToListDropable<T>? {
        val recyclerView = getRecyclerView(view) as? RecyclerView

        if (recyclerView != null) {
            return recyclerView.adapter as? ListToListDropable<T>
        } else {
            return view as? ListToListDropable<T>
        }
    }

}