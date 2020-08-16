package at.naske.microdo.lib.parking

import android.view.*
import at.naske.microdo.lib.MicroDoDragEvent

/**
 * OnDragListener to accept a dropped view on the Parking area and add it to it.
 */
class ParkingOnDragListener(
    private val mParkable: Parkable
) : View.OnDragListener {

    override fun onDrag(view: View, event: DragEvent): Boolean {

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                return true
            }
            DragEvent.ACTION_DROP -> {
                val localState = event.localState as? MicroDoDragEvent
                val viewToMove = localState?.mView
                viewToMove?.let {
                    mParkable.addParkedView(viewToMove, event.x, event.y, localState)
                    return true
                }
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
        }
        return false
    }


}