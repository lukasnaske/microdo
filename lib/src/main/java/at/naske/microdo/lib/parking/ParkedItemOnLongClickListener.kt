package at.naske.microdo.lib.parking

import android.view.View
import android.view.ViewGroup
import at.naske.microdo.lib.MicroDoDragEvent

/**
 * {@link View.OnLongClickListener} that hides the View it was attached to on starting the drag and
 * drop behaviour and adds the original event it was dropped with into the ParkingView into the
 * MicroDoDragEvent again.
 */
class ParkedItemOnLongClickListener(
    private val mOriginalEvent: MicroDoDragEvent? = null
) : View.OnLongClickListener {

    override fun onLongClick(view: View): Boolean {
        ParkingDragInitializer().startDrag(view, mOriginalEvent)
        return false
    }
}