package at.naske.microdo.lib.multimove

/**
 * The dto encapsulating an item and its original position in case the drag and drop is cancelled.
 */
data class MultiMoveDto<T>(
    val mItem: T,
    val mOriginalPosition: Int
)