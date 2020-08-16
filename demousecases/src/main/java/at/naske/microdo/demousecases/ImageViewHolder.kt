package at.naske.microdo.demousecases

import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import at.naske.microdo.demousecases.R
import kotlinx.android.synthetic.main.demo_video_view_horizontal.view.*


class ImageViewHolder(
    val view : View,
    val parent: ViewGroup
) : RecyclerView.ViewHolder(view) {
    val mVideoPreview: ImageView = view.video_preview
}