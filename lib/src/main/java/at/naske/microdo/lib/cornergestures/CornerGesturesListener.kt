package at.naske.microdo.lib.cornergestures

/**
 * The CornerGestureListener is responsible to handle the hover and swipe events towards the corners of a view.
 */
interface CornerGesturesListener {

    /**
     * Handles the hovering over the given corner. If the given corner is null, no hover is currently happening
     * over any of the corners.
     *
     * @param corner The corner currently hovered over, null if the swipe is currently not over any of the corners.
     */
    fun onHover(corner: CornerGesturesOnTouchListener.Corner?)

    /**
     * Handles the swipe to one of the corners. If the given corner is null, the swipe did not end on any of the corners.
     * @param corner The corner the swipe ended at. If null the swipe ended but not at any of the corners and only a reset
     * should happen is necessary.
     */
    fun onSwipeTo(corner: CornerGesturesOnTouchListener.Corner?)

}