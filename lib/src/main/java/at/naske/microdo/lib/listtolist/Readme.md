# List To List

List To List is a feature that eases starting a drag and drop action within a recycler view. In 
addition it lets you add elements to a recyclerview in the same fashion. This way it gets
easier to drag elements from one view to another, as long as they handle items of the same type
or a subtype.

## How to use

When creating the `RecyclerView.Adapter` for your `RecyclerView`, extend the generic `ListToListAdapter`.
This automatically attaches the `ListToListOnDragListener` to your `RecyclerView`. If you want to 
use the provided `ListToListOnLongClickListener` to start the drag and drop on long click of an 
element, simply call `onBindViewHolder(holder, position)`. You can also just attach the 
`ListToListOnLongClickListener` at any time later.

```kotlin
class DemoListToListAdapter(mItems: MutableList<Video>): ListToListAdapter<Video, VideoViewHolder>(mItems) {
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = mItems[position]
        fillData(holder, item)
        super.onBindViewHolder(holder, position)
    }
}
```

If you want to introduce a different behaviour to start the drag and drop, e.g. by using a different
listener, you can use the `ListToListDragInitializer` to start the drag and drop in the same manner
the `ListToListOnLongClickListener` would have done. The `ListToListDragInitializer` requires a  
`ListToListAdapter` in the constructor, and then you can call the `startDrag(item: T, viewToDrag: View)`
 function. The item has to be the item you want to drag.
 
 ```kotlin
    val dragInitializer = ListToListDragInitializer(listToListAdapter)
    dragInitializer.startDrag(item, view)
```


