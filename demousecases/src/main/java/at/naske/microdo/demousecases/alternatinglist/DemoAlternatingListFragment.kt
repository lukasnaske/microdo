package at.naske.microdo.demousecases.alternatinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import at.naske.microdo.demousecases.*
import at.naske.microdo.lib.alternatinglist.AlternatingLayoutManager
import kotlinx.android.synthetic.main.demo_list_to_list_list.view.*

class DemoAlternatingListFragment : Fragment() {
    /**
     * Default path is set to microdo
     */
    var mFolderPath: String = "microdo"

    private val mFiles = ArrayList<Video>()
    private lateinit var mAdapter: DemoAlternatingListRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val folderPath = it.getString("path")
            folderPath?.let {
                this.mFolderPath = folderPath
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parent = inflater.inflate(R.layout.demo_alternating_list, container, false)
        val view = parent.list
        // Set the mAdapter
        val vertical = true
        val alternatingLayoutManager = AlternatingLayoutManager(vertical)

        if (view is RecyclerView) {
            val alternatingListRecyclerViewAdapter =
                DemoAlternatingListRecyclerViewAdapter()
            alternatingListRecyclerViewAdapter.mVertical = vertical
            mAdapter = alternatingListRecyclerViewAdapter
            with(view) {
                layoutManager = alternatingLayoutManager
                adapter = mAdapter
            }
        }

        return parent
    }

    override fun onStart() {
        super.onStart()
        mFiles.clear()
        view?.let {
            VideoReader.addVideoAddable(mAdapter)
        }
    }
}