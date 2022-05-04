package com.example.happyfeets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import getJsonDataFromAsset
import org.json.JSONArray
import org.json.JSONTokener
import java.io.File
import java.io.InputStream
import java.net.URL

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
        val jsonFileString = getJsonDataFromAsset(this.requireContext(), "data.json")
        val gson = Gson()
        val listDataType = object : TypeToken<List<road>>() {}.type
        val roads: List<road> = gson.fromJson(jsonFileString, listDataType)
        for (i in 0 until roads.size) {
            dataText = dataText.plus(roads[i].title)
            dataDesc = dataDesc.plus(roads[i].desc)
        }
        recyclerView = view.findViewById(R.id.mainItemList)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = RecyclerAdapter(dataText, dataDesc)
    }
}