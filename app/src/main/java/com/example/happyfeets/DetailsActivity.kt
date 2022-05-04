package com.example.happyfeets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView

class DetailsActivity : AppCompatActivity() {

    private var title: String? = null
    private var desc: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        title = intent.getStringExtra("TITLE")
        desc = intent.getStringExtra("DESC")
        val fragment = DetailsFragment.newInstance(title, desc)
        supportFragmentManager.beginTransaction()
            .add(R.id.detailFragmentContainerView, fragment)
            .commit()
    }
}