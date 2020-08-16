package at.naske.microdo.lib.listtolist

import android.view.View
import at.naske.microdo.lib.ItemDropFailedHandler
import at.naske.microdo.lib.MicroDoDragEvent
import at.naske.microdo.lib.MutableRecyclerViewAdapter

/**
 * {@link MicroDoDragEvent} for the List-To-List behaviour. Stores the item that is being dragged,
 * the original position and the adapter it orignated from.
 */
class ListToListDragEvent<T>(
    val mItem: T,
    val mPosition: Int,
    val mOriginalAdapter: MutableRecyclerViewAdapter<T, *>,
    view: View,
    onNotDroppedListener: ItemDropFailedHandler
) : MicroDoDragEvent(view, onNotDroppedListener)