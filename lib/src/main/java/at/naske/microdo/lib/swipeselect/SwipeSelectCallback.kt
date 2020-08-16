package at.naske.microdo.lib.swipeselect

import android.view.View

/**
 * The SwipeSelectCallback is responsible for providing the information which position is below given positions
 * as well as inform the caller about a selected element and when the swipe has ended.
 */
interface SwipeSelectCallback<T> {

    /**
     * Callback to notify the caller about that a position ahs been selected. Is only called once for every item
     * until the SwipeSelect has been reset.
     * @param item The item that has been selected
     */
    fun select(item: T)

    /**
     * Callback to identify the position below the given coordinates.
     *
     * @param x The x coordinate where the current select is happening
     * @param y The y coordinate where the current select is happening
     *
     * @return The position of the element below the given x and y coordinate, -1 if no element is below the given
     * coordinates.
     */
    fun getItemBelow(x: Float, y: Float) : T?

    /**
     * Callback that returns the item <T> held within the view. This is used when the SwipeSelectOnTouchListener
     * is used as an OnClickListener on the views that should be selected.
     *
     * @param view The view that held the item that was selected
     * @return The item held within @param view, null if no item is held within this view
     */
    fun getItemIn(view: View) : T?

    /**
     * This callback is called when the user no longer touches the display, which ends the current select.
     *
     * @param selectedItems The items that were selected
     */
    fun swipeEnded(selectedItems: Collection<T>)
}