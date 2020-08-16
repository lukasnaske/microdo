package at.naske.microdo.lib

import android.view.View

/**
 * Base class for DragEvents within the MicroDo library. Requires the orignal View that was dragged
 * as well as an optional listener to handle if an element was not sucessfully dropped.
 */
open class MicroDoDragEvent(
    var mView: View,
    var onNotDroppedListener: ItemDropFailedHandler
) {
    var dropped: Boolean = false
}