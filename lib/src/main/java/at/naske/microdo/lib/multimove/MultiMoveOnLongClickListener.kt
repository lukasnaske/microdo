package at.naske.microdo.lib.multimove

import android.view.View

/**
 * {@link View.OnLongClickListener} that initializes the MultiMove drag and drop behaviour on the attached
 * View on a long click. Default implementation given within the Library, reimplement in other listeners
 * to start MultiMove for other gestures.
 */
class MultiMoveOnLongClickListener<T>(
    val mMultiMoveDragInitializer: MultiMoveDragInitializer<T>
): View.OnLongClickListener {

    override fun onLongClick(view: View): Boolean {
        mMultiMoveDragInitializer.startMultiMove(view)
        return true
    }
}