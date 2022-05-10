package com.example.happyfeets

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels

class StopperFragment : Fragment() {

    private var textViewTimer: TextView? = null
    private var buttonStartStop: Button? = null
    private var buttonSaveTime: Button? = null
    private var isRunning = false
    private var currentTime= 0
    private var textTitle = ""
    private val stopperViewModel: StopperViewModel by activityViewModels()
    lateinit var mainHandler: Handler
    private var aaaaaa = 0

    /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewTimer = view.findViewById(R.id.textViewStoperFragment)
        buttonStartStop = view.findViewById(R.id.buttonStartStop)
        buttonStartStop?.setOnClickListener() {
            isRunning = !isRunning
            if (isRunning) {
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
        return inflater.inflate(R.layout.fragment_stopper, container, false)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(handleTimer)
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(handleTimer)
    }

    private val handleTimer = object : Runnable {
        override fun run() {
            if (isRunning) currentTime++
            stopperViewModel.currentTime(currentTime)
            stopperViewModel.isRunning(isRunning)
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
        if (isRunning) {
            buttonStartStop?.text = getString(R.string.stop_button)
        } else {
            buttonSaveTime?.text = getString(R.string.start_button)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String?, currentTime: Int, isRunning: Boolean?): StopperFragment = StopperFragment().apply {
            arguments = Bundle().apply {
                putString("TITLE", title)
                putInt("CURRENT_TIME", currentTime)
                if (isRunning != null) {
                    putBoolean("IS_RUNNING", isRunning)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        textTitle = arguments?.getString("TITLE").toString()
        currentTime = arguments?.getInt("CURRENT_TIME")!!
        isRunning = arguments?.getBoolean("IS_RUNNING")!!
    }
}