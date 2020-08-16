# Parking Area

The Parking Area is a view that simply accepty any given view when dropped, reduces its size and allows
for it to be taken out again. It accepts any `MicroDoDragEvent` to take views.  

## How to use

To include a parking view simply include a `ParkingLayout` within your XML. The `scaleValueX`and 
`scaleValueY` attribute define by how much the parked view should be scaled. By default this value
is `0.5`, if no scaling should happen simply use `1`. 

```xml
<at.naske.microdo.lib.parking.ParkingLayout xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:tools="http://schemas.android.com/tools"
           xmlns:custom="http://schemas.android.com/apk/res-auto"
           android:id="@+id/parking_relative_layout"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:background="@android:color/darker_gray"
           tools:context=".parking.ParkingFragment"
           custom:scaleValueX="0.5"
           custom:scaleValueY="0.5">
   
   </at.naske.microdo.lib.parking.ParkingLayout>
```

It also automatically attaches the 
`ParkingOnLongClickListener`, which allows the view to be dragged out of the parking area again
by long click. To change this you have to extend the `ParkingLayout` and override the `addParkedView(viewToMove: View, x: Float, y: Float, originalEvent: MicroDoDragEvent?)`
function. There all you have to do is call the `addContainer`, which adds the dragged view with the
default behaviour (but can be overriden as well) and returns the view used within the parking area.
Then you can attach a different listener to the view to update the drag behaviour. To initialize
the drag you can use the `ParkingDragInitializer` which has the function `startDrag(viewToDrag: View, originalEvent: MicroDoDragEvent?)` 
that starts the drag. If you want to keep the information sent via drag and drop to the parking view,
don't forget to include the orignal sent event.

```kotlin
    override fun addParkedView(viewToMove: View, x: Float, y: Float, originalEvent: MicroDoDragEvent?) {
        val view = addToContainer(viewToMove, x, y)
        // attach custom listener to change behaviour on how to remove the view using ParkingDragInitializer
    }
```
