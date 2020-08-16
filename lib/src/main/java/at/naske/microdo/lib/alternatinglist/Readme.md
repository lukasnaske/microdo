# Alternating List

The alternating list was build upon the design of [Emre Babur on baddotech.badoo.com](https://badootech.badoo.com/a-custom-layoutmanager-case-beeline-on-android-d8d31526596b).
It is a layout manager usable within RecyclerViews that allows both horizontal and vertical lists that visualizes
the elements offset per row or column. This leads to a staggered look. 

## Examples

<img src="../../../../../../../../../demoimages/alternating_list_vertical.jpg" width="200" />
Example of vertical AlternatingList
<img src="../../../../../../../../../demoimages/alternating_list_horizontal.jpg" width="200" />
Example of horizontal AlternatingList

## How to use

Add the AlternatingLayoutManager to your recycler view as layout manager via:

```kotlin
recyclerView.layoutManager = AlternatingLayoutManager()
```

The AlternatingLayoutManager can be modified using the `AlternatingLayoutManager.ConfigLookup` and 
by simply setting if it should be vertical or horizontal by setting the `mVertical: Boolean` variable 
within the AlternatingLayoutManager.

