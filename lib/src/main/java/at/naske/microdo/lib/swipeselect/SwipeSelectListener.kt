package at.naske.microdo.lib.swipeselect

import android.view.MotionEvent
import android.view.View
import kotlin.collections.HashSet

/**
 * Acts as an OnTouchListener and enables selecting multiple elements within any view. Can automatically reset the
 * selection if the swipe ended or can be reset manually. Any selected element is reported back using the given
 * callback once until reset. Also reports if a swipe has ended.
 *
 * @constructor Stores the callback to get the positions below the coordinates from and to report the results to. If
 * mAutoReset is set to false, the selected state of elements is only reset on manual reset by the caller.
 */
class SwipeSelectListener<T>(
    private val mSwipeSelectCallback: SwipeSelectCallback<T>,
    private var mAutoReset: Boolean = true
) : View.OnTouchListener, View.OnClickListener {

    // Used to store if a new select started and it has to be reset if auto reset is turned on
    private var shouldSelect = false

    var selectedItemsList: MutableSet<T> = HashSet()

    /**
     * Handles on MotionEvents and stores selected positions. If a position has not been selected before, the callback
     * is notified using the select(int: Int) method.
     */
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                handleMovement(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                handleMovement(event.x, event.y)
                shouldSelect = false
                mSwipeSelectCallback.swipeEnded(selectedItemsList)
            }
            MotionEvent.ACTION_CANCEL -> {
                shouldSelect = false
                mSwipeSelectCallback.swipeEnded(selectedItemsList)
            }
        }
        return false
    }

    fun handleMovement(x: Float, y: Float) {
        if (!shouldSelect) {
            shouldSelect = true
            if (mAutoReset) {
                reset()
            }
        }
        val item = mSwipeSelectCallback.getItemBelow(x, y)
        item?.let {
            if (!selectedItemsList.contains(it)) {
                selectedItemsList.add(it)
                mSwipeSelectCallback.select(it)
            }
        }
    }

    /**
     * Resets the SwipeSelectOnTouchListeners state.
     */
    fun reset() {
        selectedItemsList.clear()
    }

    override fun onClick(v: View) {
        val item = mSwipeSelectCallback.getItemIn(v)
        if (mAutoReset) {
            reset()
        }
        item?.let {
            if (!selectedItemsList.contains(it)) {
                selectedItemsList.add(it)
                mSwipeSelectCallback.select(it)
            }
        }
    }

}