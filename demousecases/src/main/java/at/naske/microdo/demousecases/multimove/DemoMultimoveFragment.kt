package at.naske.microdo.demousecases.multimove

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
import kotlinx.android.synthetic.main.demo_fragment_multimove.view.*

class DemoMultimoveFragment: Fragment() {

    var mFolderPath: String = "microdo"

    private val mFiles = ArrayList<Video>()
    lateinit var mAdapter : DemoMultimoveAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.demo_fragment_multimove, container, false)
        with (view.multimove_list as RecyclerView) {
            mAdapter = DemoMultimoveAdapter()
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