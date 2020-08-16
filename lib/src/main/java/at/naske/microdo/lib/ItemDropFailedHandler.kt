package at.naske.microdo.lib

import android.view.View

/**
 * Used to undo any changes on a View if the element could not be dropped on the View it should have
 * been dropped on.
 */
interface ItemDropFailedHandler {

    /**
     * Called if the drag and drop was not successful to put the element back into its original
     *  position.
     */
    fun onFailedDrop(view: View, microDoDragEvent: MicroDoDragEvent): Boolean
}