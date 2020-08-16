package at.naske.microdo.demousecases.swipeselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import at.naske.microdo.demousecases.Video
import at.naske.microdo.demousecases.R
import at.naske.microdo.demousecases.VideoReader
import at.naske.microdo.lib.alternatinglist.AlternatingLayoutManager
import kotlinx.android.synthetic.main.demo_fragment_swipeselect.view.*

class DemoSwipeselectFragment: Fragment() {

    var mFolderPath: String = "microdo"

    private val mFiles = ArrayList<Video>()
    lateinit var mAdapter :DemoSwipeselectAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.demo_fragment_swipeselect, container, false)
        with (view.swipeselect_list as RecyclerView) {
            mAdapter = DemoSwipeselectAdapter()
            adapter = mAdapter
            layoutManager = AlternatingLayoutManager()
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        mFiles.clear()
        view?.let {
            VideoReader.addVideoAddable(mAdapter)
        }

    }
}