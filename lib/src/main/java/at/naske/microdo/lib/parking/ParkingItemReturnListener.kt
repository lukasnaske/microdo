package at.naske.microdo.lib.parking

import android.view.View
import at.naske.microdo.lib.ItemDropFailedHandler
import at.naske.microdo.lib.MicroDoDragEvent

/**
 * If a view was not dropped on any other view it is readded to the ParkingLayout if it originated from
 * there. This readdition is handled by the ParkingItemReturnListener
 */
class ParkingItemReturnListener : ItemDropFailedHandler {

    @Synchronized
    override fun onFailedDrop(view: View, microDoDragEvent: MicroDoDragEvent): Boolean {
        microDoDragEvent.mView.visibility = View.VISIBLE
        return true
    }

}