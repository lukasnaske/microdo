package at.naske.microdo.lib

import androidx.recyclerview.widget.RecyclerView
import at.naske.microdo.lib.listtolist.ListToListDropable


abstract class MutableRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder>: RecyclerView.Adapter<VH>(), ListToListDropable<T> {

    /**
     * Removes an item from the used list. <p/>
     * When using the removeItem function consider, that it uses the default .remove function of the list. This means
     * that if 2 or more elements equal the item to be removed, only the first one will be removed. To avoid this
     * override the equals method for your object and assign them a unique id.
     *
     * @param item The element to remove from the item list
     */
    abstract fun removeItem(item: T) : Int
}
