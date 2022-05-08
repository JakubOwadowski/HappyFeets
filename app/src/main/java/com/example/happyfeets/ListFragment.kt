package com.example.happyfeets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var dataText = emptyArray<String>()
    private var dataDesc = emptyArray<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val roadsDbHelper = RoadsDbHelper(this.context)
        val data = roadsDbHelper.viewData()
        for (i in data.indices) {
            dataText = dataText.plus(data[i].title)
            dataDesc = dataDesc.plus(data[i].desc)
        }
        recyclerView = view.findViewById(R.id.mainItemList)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = RecyclerAdapter(dataText, dataDesc)
    }
}