package at.naske.microdo.lib.listtolist

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * {@link View.OnLongClickListener} that initializes the List-To-List drag and drop behaviour on the attached
 * View on a long click. Default implementation given within the Library, reimplement in other listeners
 * to start List-To-List for other gestures.
 */
class ListToListOnLongClickListener<T>(
    val listToListDragInitializer: ListToListDragInitializer<T>,
    val item: T
) : View.OnLongClickListener {

    override fun onLongClick(view: View): Boolean {
        listToListDragInitializer.startDrag(item, view)
        return true
    }


}