package at.naske.microdo.demousecases.parking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import at.naske.microdo.demousecases.R
import at.naske.microdo.demousecases.VideoReader
import at.naske.microdo.demousecases.listtolist.DemoListToListRecyclerViewAdapter
import at.naske.microdo.lib.alternatinglist.AlternatingLayoutManager
import kotlinx.android.synthetic.main.demo_list_to_list_parking_fragment.view.*

class DemoListToListParkingFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.demo_list_to_list_parking_fragment, container, false)
        view.list.layoutManager = AlternatingLayoutManager()
        val adapter = DemoListToListRecyclerViewAdapter()
        view.list.layoutManager = AlternatingLayoutManager()
        view.list.adapter = adapter
        VideoReader.addVideoAddable(adapter)
        return view
    }

}