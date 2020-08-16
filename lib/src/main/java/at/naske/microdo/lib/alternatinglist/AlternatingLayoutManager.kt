package at.naske.microdo.lib.alternatinglist


import android.content.Context
import android.os.Parcel
import android.os.Parcelable

import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

/**
 * Layoutmanager supporting a alternating list layout manager for RecyclerViews. Heavily based on the article from
 * Emre Babur on baddotech.badoo.com (https://badootech.badoo.com/a-custom-layoutmanager-case-beeline-on-android-d8d31526596b).
 * Enhanced by supporting both vertical as well as horizontal layout.
 *
 * The AlternatingLayout defines a RecyclerViewLayout that creates a staggered layout, where views are added from left to right
 * and appear slightly offset per row.
 */
open class AlternatingLayoutManager(
    private val mVertical: Boolean = true
) : RecyclerView.LayoutManager() {

    /**
     * Enum used do define whether to gravitate towards the start or the end of the current position. For horizontal
     * this means towards the top (START) or the bottom (END), forv vertical towards the left (START) or the right (END)
     */
    enum class Gravity {
        START, END
    }

    /**
     * The state is used to store where the current scroll position has been on reload of the list.
     */
    data class State(val anchorPosition: Int, val anchorOffset: Int) : Parcelable {

        constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt())

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeInt(anchorPosition)
            dest.writeInt(anchorOffset)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<State> {
            override fun createFromParcel(parcel: Parcel): State = State(parcel)
            override fun newArray(size: Int): Array<State?> = arrayOfNulls(size)
        }
    }

    /**
     * Layout params specific to the AlternatingLayout
     */
    class AlternatingListLayoutParams : RecyclerView.LayoutParams {
        constructor(width: Int, height: Int) : super(width, height)
        constructor(source: ViewGroup.MarginLayoutParams) : super(source)

        /**
         * If bigger than 1, also multiple columns can taken up by one view
         */
        var spanSize: Int = 1
        /**
         * Can be used to modify the Z value of a view
         */
        var zIndex: Float = 1f
        /**
         * If false, the location of the view is calculated by using the top of the previous child view instead of the
         * bottom.
         */
        var isSolid: Boolean = true
        /**
         * Percentage view should overlay each other vertically
         */
        var verticalOverlay: Float = 0f
        /**
         * Percentage view should overlay each other horizontally
         */
        var horizontalOverlay: Float = 0f
        /**
         * The gravity of the views as defined on {@see Gravity}
         */
        var gravity: Gravity = Gravity.START
        /**
         * The margin between to elements vertically
         */
        val verticalMargin: Int
            get() = topMargin + bottomMargin
        /**
         * The margin between to elements horizontally
         */
        val horizontalMargin: Int
            get() = leftMargin + rightMargin
    }

    /**
     * Interface used to make some values customisable based on the position of the current view.
     */
    interface ConfigLookup {
        fun getSpanSize(position: Int): Int
        fun getZIndex(position: Int): Float
        fun isSolid(position: Int): Boolean
        fun getVerticalOverlay(position: Int): Float
        fun getHorizontalOverlay(position: Int): Float
        fun getGravity(position: Int): Gravity
    }

    var configLookup: ConfigLookup? = AlternatingListConfigLookup()

    private var anchorPosition = 0
    private var anchorOffset = 0

    private val parentTop: Int
        get() = paddingTop

    private val parentBottom: Int
        get() = height - paddingBottom

    private val parentLeft: Int
        get() = paddingLeft

    private val parentRight: Int
        get() = width - paddingRight

    private val parentHorizontalCenter: Int
        get() = width / 2

    private val parentVerticalCenter: Int
        get() = height / 2

    private val parentWidth: Int
        get() = parentRight - parentLeft

    private val columnWidth: Int
        get() = parentWidth / 2

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        AlternatingListLayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams): RecyclerView.LayoutParams {
        val recyclerViewLayoutParams = super.generateLayoutParams(lp)
        return AlternatingListLayoutParams(recyclerViewLayoutParams)
    }

    override fun generateLayoutParams(c: Context, attrs: AttributeSet): RecyclerView.LayoutParams {
        val recyclerViewLayoutParams = super.generateLayoutParams(c, attrs)
        return AlternatingListLayoutParams(recyclerViewLayoutParams)
    }

    override fun onSaveInstanceState(): Parcelable = State(anchorPosition, anchorOffset)

    override fun onRestoreInstanceState(state: Parcelable?) {
        (state as? State)?.let {
            anchorPosition = state.anchorPosition
            anchorOffset = state.anchorOffset
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        if (state.itemCount <= 0) return
        fillEnd(recycler, state.itemCount)
    }

    override fun scrollToPosition(position: Int) {
        anchorPosition = position
        anchorOffset = 0
        requestLayout()
    }

    override fun canScrollVertically(): Boolean = mVertical

    override fun canScrollHorizontally(): Boolean = !mVertical

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int =
        when {
            // Do nothing if no children attached
            childCount == 0 -> 0
            // If scrolling to the left, load views to the left
            dx < 0 -> {
                val availableTop = if (clipToPadding) parentLeft else 0
                var scrolled = 0
                while (scrolled > dx) {
                    val firstChild = getChildAt(0)!!
                    val firstChildLeft = getDecoratedLeft(firstChild) - firstChild.layoutParams().leftMargin
                    val hangingLeft = max(0, availableTop - firstChildLeft)
                    val scrollBy = min(hangingLeft, scrolled - dx)
                    offsetChildrenHorizontallyBy(-scrollBy)
                    scrolled -= scrollBy
                    if (anchorPosition == 0) break
                    fillStart(recycler)
                }
                scrolled
            }
            // If scrolling to the right, load views to the right
            dx > 0 -> {
                val availableRight = if (clipToPadding) parentRight else width
                var scrolled = 0
                while (scrolled < dx) {
                    val lastChild = getChildAt(childCount - 1)!!
                    val lastChildPosition = getPosition(lastChild)
                    val layoutParams = lastChild.layoutParams()
                    val lastChildRight = getDecoratedRight(lastChild) + layoutParams.rightMargin
                    val hangingRight = max(0, lastChildRight - availableRight)
                    val scrollBy = min(hangingRight, dx - scrolled)
                    offsetChildrenHorizontallyBy(scrollBy)
                    scrolled += scrollBy
                    if (lastChildPosition == state.itemCount - 1) break
                    fillEnd(recycler, state.itemCount)
                }
                scrolled
            }
            // Do nothing if no scrolling actually happened
            else -> 0
        }
            .also {
                recycleViewsOutOfBounds(recycler)
                updateAnchorOffset()
            }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int =
        when {
            // Do nothing if no children attached
            childCount == 0 -> 0
            // If scrolling to the top, load views to the top
            dy < 0 -> {
                val availableTop = if (clipToPadding) parentTop else 0
                var scrolled = 0
                while (scrolled > dy) {
                    val firstChild = getChildAt(0)!!
                    val firstChildTop = getDecoratedTop(firstChild) - firstChild.layoutParams().topMargin
                    val hangingTop = max(0, availableTop - firstChildTop)
                    val scrollBy = min(hangingTop, scrolled - dy)
                    offsetChildrenVerticallyBy(-scrollBy)
                    scrolled -= scrollBy
                    if (anchorPosition == 0) break
                    fillStart(recycler)
                }
                scrolled
            }
            // If scrolling to the bottom, load views to the bottom
            dy > 0 -> {
                val availableBottom = if (clipToPadding) parentBottom else height
                var scrolled = 0
                while (scrolled < dy) {
                    val lastChild = getChildAt(childCount - 1)!!
                    val lastChildPosition = getPosition(lastChild)
                    val layoutParams = lastChild.layoutParams()
                    val lastChildBottom = getDecoratedBottom(lastChild) + layoutParams.bottomMargin
                    val hangingBottom = max(0, lastChildBottom - availableBottom)
                    val scrollBy = min(hangingBottom, dy - scrolled)
                    offsetChildrenVerticallyBy(scrollBy)
                    scrolled += scrollBy
                    if (lastChildPosition == state.itemCount - 1) break
                    fillEnd(recycler, state.itemCount)
                }
                scrolled
            }
            // Do nothing if no scrolling actually happened
            else -> 0
        }
            .also {
                recycleViewsOutOfBounds(recycler)
                updateAnchorOffset()
            }

    /**
     * Fills the end of the list, either bottom or right depending if the alternating list is horizontal or vertical
     */
    open fun fillEnd(recycler: RecyclerView.Recycler, adapterItemCount: Int) {
        if (mVertical) {
            fillBottom(recycler, adapterItemCount)
        } else {
            fillRight(recycler, adapterItemCount)
        }
    }

    /**
     * Fills the start of the list, either top or left depending if the alternating list is horizontal or vertical
     */
    open fun fillStart(recycler: RecyclerView.Recycler) {
        if (mVertical) {
            fillTop(recycler)
        } else {
            fillLeft(recycler)
        }
    }

    /**
     * Recycles views that are no longer visible to reuse them and save memory
     */
    open fun recycleViewsOutOfBounds(recycler: RecyclerView.Recycler) {

        if (childCount == 0) return
        val childCount = childCount

        var firstVisibleChild = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)!!
            val layoutParams = child.layoutParams()
            val top = if (clipToPadding) parentTop else 0
            if (getDecoratedBottom(child) + layoutParams.bottomMargin < top) {
                firstVisibleChild++
            } else {
                break
            }
        }

        var lastVisibleChild = firstVisibleChild
        for (i in lastVisibleChild until childCount) {
            val child = getChildAt(i)!!
            val layoutParams = child.layoutParams()
            if (getDecoratedTop(child) - layoutParams.topMargin <= if (clipToPadding) parentBottom else height) {
                lastVisibleChild++
            } else {
                lastVisibleChild--
                break
            }
        }

        for (i in childCount - 1 downTo lastVisibleChild + 3) removeAndRecycleViewAt(i, recycler)
        for (i in firstVisibleChild - 1 downTo 0) removeAndRecycleViewAt(i, recycler)
        anchorPosition += firstVisibleChild
    }

    open fun fillBottom(recycler: RecyclerView.Recycler, adapterItemCount: Int) {

        var hardTop: Int
        var softTop: Int

        var startPosition: Int

        if (childCount > 0) {
            val lastChild = getChildAt(childCount - 1)!!
            val lastChildPosition = getPosition(lastChild)
            startPosition = lastChildPosition + 1
            val lp = lastChild.layoutParams()

            hardTop =
                if (lp.isSolid) {
                    getDecoratedBottom(lastChild) + lp.bottomMargin - ((lastChild.measuredHeight + lp.verticalMargin) * lp.verticalOverlay).toInt()
                } else {
                    getDecoratedTop(lastChild)
                }
            softTop = getDecoratedBottom(lastChild) + lp.bottomMargin
        } else {
            hardTop = parentTop + if (anchorPosition < adapterItemCount) anchorOffset else 0
            softTop = parentTop + if (anchorPosition < adapterItemCount) anchorOffset else 0

            startPosition = if (anchorPosition < adapterItemCount) anchorPosition else 0
        }

        val availableBottom = if (clipToPadding) parentBottom else height

        for (i in startPosition until adapterItemCount) {

            if (hardTop > availableBottom) break

            val view = recycler.getViewForPosition(i)
            addView(view)
            view.setAlternatingListLayoutParams(i)
            view.measure()
            val lp = view.layoutParams()
            if (lp.isSolid) {
                val top = max(
                    hardTop,
                    softTop - ((view.measuredHeight + lp.verticalMargin) * lp.verticalOverlay).toInt()
                )
                val bottom = top + view.measuredHeight + lp.verticalMargin
                layoutViewVertical(view, top, bottom)
                hardTop = bottom - ((view.measuredHeight + lp.verticalMargin) * lp.verticalOverlay).toInt()
                softTop = bottom
            } else {
                val top = hardTop
                val bottom = top + view.measuredHeight + lp.verticalMargin
                layoutViewVertical(view, top, bottom)
                softTop = bottom
            }
        }
    }

    open fun fillRight(recycler: RecyclerView.Recycler, adapterItemCount: Int) {

        var hardLeft: Int
        var softLeft: Int

        var startPosition: Int

        if (childCount > 0) {
            val lastChild = getChildAt(childCount - 1)!!
            val lastChildPosition = getPosition(lastChild)
            startPosition = lastChildPosition + 1
            val lp = lastChild.layoutParams()

            hardLeft =
                if (lp.isSolid) {
                    getDecoratedRight(lastChild) + lp.rightMargin - ((lastChild.measuredWidth + lp.horizontalMargin) * lp.horizontalOverlay).toInt()
                } else {
                    getDecoratedLeft(lastChild)
                }
            softLeft = getDecoratedBottom(lastChild) + lp.bottomMargin
        } else {
            hardLeft = parentLeft + if (anchorPosition < adapterItemCount) anchorOffset else 0
            softLeft = parentLeft + if (anchorPosition < adapterItemCount) anchorOffset else 0

            startPosition = if (anchorPosition < adapterItemCount) anchorPosition else 0
        }

        val availableRight = if (clipToPadding) parentRight else width

        for (i in startPosition until adapterItemCount) {

            if (hardLeft > availableRight) break

            val view = recycler.getViewForPosition(i)
            addView(view)
            view.setAlternatingListLayoutParams(i)
            view.measure()
            val lp = view.layoutParams()
            if (lp.isSolid) {
                val left = max(
                    hardLeft,
                    softLeft - ((view.measuredWidth + lp.horizontalMargin) * lp.horizontalOverlay).toInt()
                )
                val right = left + view.measuredWidth + lp.horizontalMargin
                layoutViewHorizontal(view, left, right)
                hardLeft = right - ((view.measuredWidth + lp.horizontalMargin) * lp.horizontalOverlay).toInt()
                softLeft = right
            } else {
                val left = hardLeft
                val right = left + view.measuredWidth + lp.horizontalMargin
                layoutViewHorizontal(view, left, right)
                softLeft = right
            }
        }
    }

    open fun fillTop(recycler: RecyclerView.Recycler) {
        if (childCount == 0) return

        val firstChild = getChildAt(0)!!
        val firstChildPosition = getPosition(firstChild)
        if (firstChildPosition == 0) return
        val lp = firstChild.layoutParams()
        var hardBottom =
            if (lp.isSolid) {
                getDecoratedTop(firstChild) - lp.topMargin + ((firstChild.measuredHeight + lp.verticalMargin) * lp.verticalOverlay).toInt()
            } else {
                getDecoratedBottom(firstChild)
            }
        var softBottom = getDecoratedTop(firstChild) - lp.topMargin
        val availableTop = if (clipToPadding) parentTop else 0

        for (i in firstChildPosition - 1 downTo 0) {

            if (hardBottom < availableTop) break

            val view = recycler.getViewForPosition(i)
            anchorPosition--
            addView(view, 0)
            view.setAlternatingListLayoutParams(i)
            view.measure()
            val lp = view.layoutParams()
            if (lp.isSolid) {
                val bottom = min(
                    hardBottom,
                    softBottom + ((view.measuredHeight + lp.verticalMargin) * lp.verticalOverlay).toInt()
                )
                val top = bottom - view.measuredHeight - lp.verticalMargin
                layoutViewVertical(view, top, bottom)
                hardBottom = top + ((view.measuredHeight + lp.verticalMargin) * lp.verticalOverlay).toInt()
                softBottom = top
            } else {
                val bottom = hardBottom
                val top = bottom - view.measuredHeight - lp.verticalMargin
                layoutViewVertical(view, top, bottom)
                softBottom = top
            }
        }
    }

    open fun fillLeft(recycler: RecyclerView.Recycler) {
        if (childCount == 0) return

        val firstChild = getChildAt(0)!!
        val firstChildPosition = getPosition(firstChild)
        if (firstChildPosition == 0) return
        val lp = firstChild.layoutParams()
        var hardRight =
            if (lp.isSolid) {
                getDecoratedLeft(firstChild) - lp.leftMargin + ((firstChild.measuredWidth + lp.horizontalMargin) * lp.horizontalOverlay).toInt()
            } else {
                getDecoratedRight(firstChild)
            }
        var softRight = getDecoratedLeft(firstChild) - lp.leftMargin
        val availableLeft = if (clipToPadding) parentLeft else 0

        for (i in firstChildPosition - 1 downTo 0) {

            if (hardRight < availableLeft) break

            val view = recycler.getViewForPosition(i)
            anchorPosition--
            addView(view, 0)
            view.setAlternatingListLayoutParams(i)
            view.measure()
            val lp = view.layoutParams()
            if (lp.isSolid) {
                val right = min(
                    hardRight,
                    softRight + ((view.measuredWidth + lp.horizontalMargin) * lp.horizontalOverlay).toInt()
                )
                val left = right - view.measuredWidth - lp.horizontalMargin
                layoutViewHorizontal(view, left, right)
                hardRight = left + ((view.measuredWidth + lp.horizontalMargin) * lp.horizontalOverlay).toInt()
                softRight = left
            } else {
                val right = hardRight
                val left = right - view.measuredWidth - lp.horizontalMargin
                layoutViewHorizontal(view, left, right)
                softRight = left
            }
        }
    }

    open fun layoutViewVertical(view: View, top: Int, bottom: Int) {
        view.translationZ = view.layoutParams().zIndex
        val (left, right) = when (view.layoutParams().gravity) {
            Gravity.START -> parentLeft to (
                    if (view.layoutParams().spanSize == 1)
                        parentHorizontalCenter
                    else
                        parentRight
                    )
            Gravity.END -> (if (view.layoutParams().spanSize == 1) parentHorizontalCenter else parentLeft) to parentRight
        }
        layoutDecoratedWithMargins(view, left, top, right, bottom)
    }

    open fun layoutViewHorizontal(view: View, left: Int, right: Int) {
        view.translationZ = view.layoutParams().zIndex
        val (top, bottom) = when (view.layoutParams().gravity) {
            Gravity.START -> parentTop to (
                    if (view.layoutParams().spanSize == 1)
                        parentVerticalCenter
                    else
                        parentBottom
                    )
            Gravity.END -> (if (view.layoutParams().spanSize == 1) parentVerticalCenter else parentTop) to parentBottom
        }
        layoutDecoratedWithMargins(view, left, top, right, bottom)
    }

    open fun updateAnchorOffset() {
        anchorOffset =
            if (childCount > 0) {
                val view = getChildAt(0)!!
                if (mVertical) {
                    getDecoratedTop(view) - view.layoutParams().topMargin - parentTop
                } else {
                    getDecoratedLeft(view) - view.layoutParams().leftMargin - parentLeft
                }
            } else {
                0
            }
    }

    open fun offsetChildrenVerticallyBy(dy: Int) {
        for (i in 0 until childCount) {
            val view = getChildAt(i)!!
            view.scrollVerticallyBy(dy)
        }
    }

    open fun offsetChildrenHorizontallyBy(dx: Int) {
        for (i in 0 until childCount) {
            val view = getChildAt(i)!!
            view.scrollHorizontallyBy(dx)
        }
    }

    open fun View.scrollVerticallyBy(dy: Int) {
        offsetTopAndBottom(-dy)
    }

    open fun View.scrollHorizontallyBy(dx: Int) {
        offsetLeftAndRight(-dx)
    }

    open fun View.layoutParams(): AlternatingListLayoutParams =
        layoutParams as AlternatingListLayoutParams

    open fun View.measure() {
        val widthUsed = if (layoutParams().spanSize == 1) columnWidth else 0
        measureChildWithMargins(this, widthUsed, 0)
    }

    open fun View.setAlternatingListLayoutParams(position: Int) {
        configLookup?.let {
            layoutParams().apply {
                spanSize = it.getSpanSize(position)
                zIndex = it.getZIndex(position)
                isSolid = it.isSolid(position)
                verticalOverlay = it.getVerticalOverlay(position)
                horizontalOverlay = it.getHorizontalOverlay(position)
                gravity = it.getGravity(position)
            }
        }
    }
}
