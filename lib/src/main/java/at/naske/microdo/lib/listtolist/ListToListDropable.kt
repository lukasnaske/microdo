package at.naske.microdo.lib.listtolist

/**
 * Classes implementing this interface can accept dropped items via the List-To-List feature.
 */
interface ListToListDropable<T> {
    /**
     * Adds the given item to the list by checking which elements lies below the given coordinates and inserting the new
     * element before. <p />
     *
     */
    fun addItem(item: T?, x: Float, y: Float): Int
    /**
     * Adds the given item at the given position.
     *
     * @param position The position in the list to add the item add. Has to be >= 0.
     * @param item The item to add to the list.
     */
    fun addItem(position: Int, item: T?): Int

    /**
     * Adds the given item at the end of the list <p/>
     *
     * @param item The item to add to the list
     */
    fun addItem(item: T?)
}