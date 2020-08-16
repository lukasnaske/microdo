package at.naske.microdo.lib.multimove

import android.view.View
import at.naske.microdo.lib.ItemDropFailedHandler
import at.naske.microdo.lib.MicroDoDragEvent
import at.naske.microdo.lib.listtolist.ListToListDragEvent

/**
 * {@link MicroDoDragEvent} for the MultiMove feature.
 */
class MultiMoveDragEvent<T> (
    mView: View,
    onNotDroppedListener: ItemDropFailedHandler,
    val itemDragEvents: List<ListToListDragEvent<T>>
): MicroDoDragEvent(mView, onNotDroppedListener) {

}