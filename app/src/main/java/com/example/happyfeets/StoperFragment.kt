package com.example.happyfeets

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class StoperFragment : Fragment() {

    private var textViewTimer: TextView? = null
    private var buttonStartStop: Button? = null
    private var buttonSaveTime: Button? = null
    private var timerRunning = false
    private var currentTime = 0
    lateinit var mainHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            currentTime = savedInstanceState.getInt("CURRENT_TIME")
            timerRunning = savedInstanceState.getBoolean("IS_RUNNING")
        }

        textViewTimer = view.findViewById(R.id.textViewStoperFragment)
        buttonStartStop = view.findViewById(R.id.buttonStartStop)
        buttonStartStop?.setOnClickListener() {
            timerRunning = !timerRunning
            if (timerRunning) {
                currentTime = 0
                changeTimer()
            } else {
                buttonStartStop?.text = getString(R.string.start_button)
            }
        }

        buttonSaveTime = view.findViewById(R.id.buttonSaveTime)
        buttonSaveTime?.setOnClickListener() {
            val timeDbHelper: TimeDbHelper = TimeDbHelper(this.context)
        }
        mainHandler = Handler(Looper.getMainLooper())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stoper, container, false)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(handleTimer)
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(handleTimer)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CURRENT_TIME", currentTime)
        outState.putBoolean("IS_RUNNING", timerRunning)
    }

    private val handleTimer = object : Runnable {
        override fun run() {
            if (timerRunning) currentTime++
            changeTimer()
            mainHandler.postDelayed(this, 1000)
        }
    }

    private fun changeTimer() {
        val hours = currentTime / 3600
        val hoursStr = if (hours < 10) "0$hours" else hours.toString()
        val minutes = (currentTime / 60) % 60
        val minutesStr = if (minutes < 10) "0$minutes" else minutes.toString()
        val seconds: Int = currentTime % 60
        val secondsStr = if (seconds < 10) "0$seconds" else seconds.toString()
        ("$hoursStr:$minutesStr:$secondsStr").also { textViewTimer?.text = it }
        if (timerRunning) {
            buttonStartStop?.text = getString(R.string.stop_button)
        } else {
            buttonSaveTime?.text = getString(R.string.start_button)
        }
    }
}