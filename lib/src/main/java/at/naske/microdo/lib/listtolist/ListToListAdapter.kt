package at.naske.microdo.lib.listtolist

import androidx.recyclerview.widget.RecyclerView
import at.naske.microdo.lib.MutableRecyclerViewAdapter


/**
 * The ListToListAdapter works as an abstract implementation for the RecyclerView adapter that nativeley enables drag
 * and drop behaviour for mItems of the generic given type T.
 */
abstract class ListToListAdapter<T, VH : RecyclerView.ViewHolder>(
    val mItems: MutableList<T>
) : MutableRecyclerViewAdapter<T, VH>(), ListToListDropable<T> {

    lateinit var mRecyclerView: RecyclerView
    override fun getItemCount(): Int = mItems.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
        recyclerView.setOnDragListener(ListToListOnDragListener<T>())
    }

    /**
     * Automatically applies the ListTolistOnLongClickListener. If another form of interaction is wanted to start the
     * drag and drop, do not call super.onBindViewHolder(holder, position).
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnLongClickListener(
            ListToListOnLongClickListener(
                ListToListDragInitializer(this), mItems[position]
            )
        )
    }

    override fun removeItem(item: T): Int {
        val index = mItems.indexOf(item)
        mItems.remove(item)
        notifyItemRemoved(index)
        return index
    }


    override fun addItem(position: Int, item: T?): Int {
        item?.let {
            mItems.add(position, item)
            notifyItemInserted(position)
        }
        return position
    }

    override fun addItem(item: T?) {
        item?.let {
            addItem(mItems.size, item)
        }
    }


    override fun addItem(item: T?, x: Float, y: Float): Int {
        val view = mRecyclerView.findChildViewUnder(x, y)
        item?.let {
            when (view) {
                null -> {
                    val position = mItems.size
                    addItem(position, item)
                    return position
                }
                else -> {
                    val viewHolderLayoutPosition =
                        mRecyclerView.findContainingViewHolder(view)?.layoutPosition
                    viewHolderLayoutPosition?.let {
                        addItem(viewHolderLayoutPosition, item)
                        return viewHolderLayoutPosition
                    }
                }
            }
        }
        return -1
    }

    open fun getPosition(x: Float, y: Float): Int {
        val view = mRecyclerView.findChildViewUnder(x, y)

        when (view) {
            null -> {
                return -1
            }
            else -> {
                val viewHolderLayoutPosition =
                    mRecyclerView?.findContainingViewHolder(view)?.layoutPosition
                viewHolderLayoutPosition?.let {
                    return viewHolderLayoutPosition
                }
            }
        }
        return -1
    }
}