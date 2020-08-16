package at.naske.microdo.lib.multimove

import android.view.View
import android.widget.TextView
import at.naske.microdo.lib.R

/**
 * Hellper class to modify the {@see MultiMoveLayout}
 */
class MultiMoveHelper {
    companion object {
        /**
         * Show the number of contained objects given in @param count in the TextView of the
         * MultiMoveLayout
         */
        fun showContainedObjectCount(view: View, count: Int) {
            val counter = view.findViewById(R.id.multimove_contained_items_count) as? TextView
            counter?.let {
                counter.visibility = View.VISIBLE
                counter.text = count.toString()
            }
        }

        /**
         * Hide the number given on the given View if the MultiMoveLayout has been used.
         */
        fun hideContainedObjectcount(view: View) {
            val counter = view.findViewById(R.id.multimove_contained_items_count) as? TextView
            counter?.let {
                counter.visibility = View.INVISIBLE
                counter.text = "0"
            }
        }
    }
}