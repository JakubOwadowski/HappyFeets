package com.example.happyfeets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class DetailsActivity : AppCompatActivity() {

    private var title: String? = null
    private var desc: String? = null
    private var currentTime = 0
    private var isRunning = false
    private val stopperViewModel: StopperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if (savedInstanceState != null) {
            currentTime = savedInstanceState.getInt("CURRENT_TIME")
            isRunning = savedInstanceState.getBoolean("IS_RUNNING")
        }
        title = intent.getStringExtra("TITLE")
        desc = intent.getStringExtra("DESC")
        val detailsFragment = DetailsFragment.newInstance(title, desc)
        val stopperFragment = StopperFragment.newInstance(title, currentTime, isRunning)
        supportFragmentManager.beginTransaction()
            .add(R.id.detailFragmentContainerView, detailsFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.stoperFragmentContainerView, stopperFragment)
            .commitNowAllowingStateLoss()
        stopperViewModel.currentTime.observe(this) { item ->
            currentTime = item
        }
        stopperViewModel.isRunning.observe(this) { item ->
            isRunning = item
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_TIME", currentTime)
        outState.putBoolean("IS_RUNNING", isRunning)
    }
}