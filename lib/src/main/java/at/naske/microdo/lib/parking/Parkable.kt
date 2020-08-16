package at.naske.microdo.lib.parking

import android.view.View
import at.naske.microdo.lib.MicroDoDragEvent

/**
 * A view that accepts a view to be parked. Used by the ParkingFragmentOnDragListener to park the item.
 * */
interface Parkable {

    /**
     * Add the given view at the given location.
     *
     * @param viewToMove The view to park
     * @param x The x coordinate where to park the view
     * @param y the y coordinate where to park the view
     * @param originalEvent The orignal MicroDoDragEvent, used for determining what actual items are displayed
     * within the view, when using the ListToList MicroDO this event can be used to move elements back
     * into a list.
     */
    fun addParkedView(viewToMove: View, x: Float, y: Float, originalEvent: MicroDoDragEvent? = null)

}