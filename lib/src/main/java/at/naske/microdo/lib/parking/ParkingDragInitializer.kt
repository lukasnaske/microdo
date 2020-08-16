package at.naske.microdo.lib.parking

import android.view.View
import at.naske.microdo.lib.MicroDoDragEvent

/**
 * Initializes drag and drop on a given View. Reuses the original MicroDoDragEvent to work with the
 * ListToList MicroDO.
 */
open class ParkingDragInitializer {

    /**
     * Starts the drag on the give View and add the information from the origianl MicroDoDragEvent.
     *
     * @param viewToDrag The view to start the drag and drop on
     * @param originalEvent The original MicroDoDragEvent that was used when dropping the view in the Parking area.
     */
    open fun startDrag(viewToDrag: View, originalEvent: MicroDoDragEvent?) {
        val localState: MicroDoDragEvent
        if (originalEvent == null) {
            localState = MicroDoDragEvent(viewToDrag, ParkingItemReturnListener())
        } else {
            localState = originalEvent
            localState.onNotDroppedListener = ParkingItemReturnListener()
            localState.mView = viewToDrag
        }
        val myShadow = View.DragShadowBuilder(viewToDrag)
        viewToDrag.startDragAndDrop(null, myShadow, localState, 0)

        // Remove the view from its parent so it can be attached to a different
        viewToDrag.visibility = View.GONE
    }
}