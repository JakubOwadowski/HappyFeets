package com.example.happyfeets

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class DetailsFragment : Fragment() {

    private var textTitle: TextView? = null
    private var textTitleText: String? = null
    private var textDesc: TextView? = null
    private var textDescText: String? = null
    private var returnButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textTitle = view.findViewById(R.id.textViewTitle)
        textTitle?.text = textTitleText
        textDesc = view.findViewById(R.id.textViewDesc)
        textDesc?.text = textDescText
        returnButton = view.findViewById(R.id.button)
        returnButton?.setOnClickListener() {
            activity?.finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String?, desc: String?) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString("TITLE", title)
                putString("DESC", desc)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        textDescText = arguments?.getString("DESC")
        textTitleText = arguments?.getString("TITLE")
    }
}