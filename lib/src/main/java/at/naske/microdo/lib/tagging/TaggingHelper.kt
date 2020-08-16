package at.naske.microdo.lib.tagging

import android.view.View
import at.naske.microdo.lib.R

class TaggingHelper(
    view: View
) {
    val mBottomTag = view.findViewById(R.id.bottom_tag) as? View
    val mTopTag = view.findViewById(R.id.top_tag) as? View
    val mLeftTag = view.findViewById(R.id.left_tag) as? View
    val mRightTag = view.findViewById(R.id.right_tag) as? View

    /**
     * Toggles the top tag if found within the controlled view to Visible or Gone based on @param visible
     * and changes the color if given to @param backgroundColor. If the tag cannot be found
     * (which most probably means the view is not contained within the tagging_container), nothing
     * happens.
     */
    fun toggleTopTag(visibility: Int? = null, backgroundColor: Int? = null) {
        mTopTag?.let {
            visibility?.let {
                mTopTag.visibility = visibility
            }
            backgroundColor?.let {
                mTopTag.setBackgroundColor(backgroundColor)
            }
        }
    }

    /**
     * Toggles the bottom tag if found within the @param view to Visible or Gone based on @param visible
     * and changes the color if given to @param backgroundColor. If the tag cannot be found
     * (which most probably means the view is not contained within the tagging_container), nothing
     * happens.
     */
    fun toggleBottomTag(visibility: Int? = null, backgroundColor: Int? = null) {
        mBottomTag?.let {
            visibility?.let {
                mBottomTag.visibility = visibility
            }
            backgroundColor?.let {
                mBottomTag.setBackgroundColor(backgroundColor)
            }
        }
    }

    /**
     * Toggles the right tag if found within the @param view to Visible or Gone based on @param visible
     * and changes the color if given to @param backgroundColor. If the tag cannot be found
     * (which most probably means the view is not contained within the tagging_container), nothing
     * happens.
     */
    fun toggleRightTag(visibility: Int? = null, backgroundColor: Int? = null) {
        mRightTag?.let {
            visibility?.let {
                mRightTag.visibility = visibility
            }
            backgroundColor?.let {
                mRightTag.setBackgroundColor(backgroundColor)
            }
        }
    }

    /**
     * Toggles the left tag if found within the @param view to Visible or Gone based on @param visible
     * and changes the color if given to @param backgroundColor. If the tag cannot be found
     * (which most probably means the view is not contained within the tagging_container), nothing
     * happens.
     */
    fun toggleLeftTag(visibility: Int? = null, backgroundColor: Int? = null) {
        mLeftTag?.let {
            visibility?.let {
                mLeftTag.visibility = visibility
            }
            backgroundColor?.let {
                mLeftTag.setBackgroundColor(backgroundColor)
            }
        }
    }


    companion object {

        /**
         * Toggles the top tag if found within the @param view to Visible or Gone based on @param visible
         * and changes the color if given to @param backgroundColor. If the tag cannot be found
         * (which most probably means the view is not contained within the tagging_container), nothing
         * happens.
         */
        fun toggleTopTag(view: View, visible: Boolean, backgroundColor: Int?) {
            val topTag = view.findViewById(R.id.top_tag) as? View
            topTag?.let {
                topTag.visibility = if (visible) View.VISIBLE else View.GONE
                backgroundColor?.let {
                    topTag.setBackgroundColor(backgroundColor)
                }
            }
        }

        /**
         * Toggles the bottom tag if found within the @param view to Visible or Gone based on @param visible
         * and changes the color if given to @param backgroundColor. If the tag cannot be found
         * (which most probably means the view is not contained within the tagging_container), nothing
         * happens.
         */
        fun toggleBottomTag(view: View, visible: Boolean, backgroundColor: Int?) {
            val bottomTag = view.findViewById(R.id.bottom_tag) as? View
            bottomTag?.let {
                bottomTag.visibility = if (visible) View.VISIBLE else View.GONE
                backgroundColor?.let {
                    bottomTag.setBackgroundColor(backgroundColor)
                }
            }
        }

        /**
         * Toggles the right tag if found within the @param view to Visible or Gone based on @param visible
         * and changes the color if given to @param backgroundColor. If the tag cannot be found
         * (which most probably means the view is not contained within the tagging_container), nothing
         * happens.
         */
        fun toggleRightTag(view: View, visible: Boolean, backgroundColor: Int?) {
            val rightTag = view.findViewById(R.id.right_tag) as? View
            rightTag?.let {
                rightTag.visibility = if (visible) View.VISIBLE else View.GONE
                backgroundColor?.let {
                    rightTag.setBackgroundColor(backgroundColor)
                }
            }
        }

        /**
         * Toggles the left tag if found within the @param view to Visible or Gone based on @param visible
         * and changes the color if given to @param backgroundColor. If the tag cannot be found
         * (which most probably means the view is not contained within the tagging_container), nothing
         * happens.
         */
        fun toggleLeftTag(view: View, visible: Boolean, backgroundColor: Int?) {
            val leftTag = view.findViewById(R.id.left_tag) as? View
            leftTag?.let {
                leftTag.visibility = if (visible) View.VISIBLE else View.GONE
                backgroundColor?.let {
                    leftTag.setBackgroundColor(backgroundColor)
                }
            }
        }

    }
}