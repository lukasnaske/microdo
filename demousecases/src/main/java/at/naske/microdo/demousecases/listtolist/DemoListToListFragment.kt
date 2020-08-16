package at.naske.microdo.demousecases.listtolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import at.naske.microdo.demousecases.*
import at.naske.microdo.demousecases.R
import at.naske.microdo.lib.alternatinglist.AlternatingLayoutManager


import kotlinx.android.synthetic.main.demo_list_to_list_list.view.*

class DemoListToListFragment : Fragment() {

    private val mFiles = ArrayList<Video>()
    private lateinit var mAdapter: DemoListToListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parent = inflater.inflate(R.layout.demo_list_to_list_list, container, false)
        val recyclerView = parent.list
        val recyclerView2 = parent.list2
        // Set the mAdapter


        mAdapter =
            DemoListToListRecyclerViewAdapter(
                mFiles
            )
        with(recyclerView) {
            layoutManager = AlternatingLayoutManager(false)
            adapter = mAdapter
        }
        with(recyclerView2) {
            layoutManager = AlternatingLayoutManager(true)
            adapter =
                DemoListToListRecyclerViewAdapter()
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
