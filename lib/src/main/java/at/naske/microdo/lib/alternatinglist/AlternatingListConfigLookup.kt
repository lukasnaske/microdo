package at.naske.microdo.lib.alternatinglist

/**
 * Default config lookup class for the alternating layout manager. The values can be either changed or overwritten by
 * creating a subclass of this config lookup.
 */
class AlternatingListConfigLookup : AlternatingLayoutManager.ConfigLookup {

    var mHorizontalOverlay = 0.5f
    var mVerticalOverlay = 0.5f
    var mSolid = true
    var mZIndex = 1f
    var mSpanSize = 1

    override fun getHorizontalOverlay(position: Int): Float {
        return mHorizontalOverlay
    }

    fun setHorizontalOverLay(horizontalOverlay: Float) : AlternatingListConfigLookup {
        this.mHorizontalOverlay = horizontalOverlay
        return this
    }

    fun setVerticalOverLay(verticalOveral: Float) : AlternatingListConfigLookup {
        this.mVerticalOverlay = verticalOveral
        return this
    }

    fun isSolid(solid: Boolean) : AlternatingListConfigLookup {
        this.mSolid = solid
        return this
    }

    fun setZIndex(zIndex: Float) : AlternatingListConfigLookup {
        this.mZIndex = zIndex
        return this
    }

    fun setSpanSize(spanSize: Int) : AlternatingListConfigLookup {
        this.mSpanSize = spanSize
        return this
    }

    override fun getVerticalOverlay(position: Int): Float {
        return mVerticalOverlay
    }

    override fun getGravity(position: Int): AlternatingLayoutManager.Gravity {
        when (position % 2) {
            1 -> {
                return AlternatingLayoutManager.Gravity.END
            }
        }
        return AlternatingLayoutManager.Gravity.START
    }

    override fun isSolid(position: Int): Boolean {
        return mSolid
    }

    override fun getZIndex(position: Int): Float {
        return mZIndex
    }

    override fun getSpanSize(position: Int): Int {
        return mSpanSize
    }
}