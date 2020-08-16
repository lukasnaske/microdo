package at.naske.microdo.lib.swipeselect

import android.view.View
import android.widget.CheckBox
import at.naske.microdo.lib.R

/**
 * Helper class to modify the checkbox on the SwipeSelectLayout.
 */
class SwipeSelectHelper {
    companion object {
        /**
         * Shows the checkbox on the given view, if @param selected is either true or false, it either
         * checks or unchecks the checkbox.
         */
        fun showSelectedItemsCheckBox(view: View, selected: Boolean? = null) {
            val checkBox = view.findViewById(R.id.swipeselect_checkbox) as? CheckBox

            checkBox?.let {
                checkBox.visibility = View.VISIBLE
                selected?.let {
                    checkBox.isChecked = selected
                }
            }
        }

        /**
         * Hides the checkbox on the given View.
         */
        fun hideSelectedItemsCheckBox(view: View) {
            val checkBox = view.findViewById(R.id.swipeselect_checkbox) as? CheckBox

            checkBox?.let {
                checkBox.visibility = View.INVISIBLE
            }
        }

        /**
         * Either checks or unchecks the checkbox on the given View if it has the SwipeSelectLayout
         * in its component tree.
         *
         * @param view the View to check the Checkbox on
         * @param selected Checks the checkbox if true, uncheck it if false. If null nothing is changed.
         */
        fun toggleCheckBox(view: View, selected: Boolean? = null) {
            val checkBox = view.findViewById(R.id.swipeselect_checkbox) as? CheckBox

            checkBox?.let {
                val checked = selected ?: !checkBox.isChecked
                checkBox.isChecked = checked
            }
        }
    }
}