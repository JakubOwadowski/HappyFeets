package com.example.happyfeets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StopperViewModel : ViewModel() {
    private val mutableCurrentTime = MutableLiveData<Int>()
    private val mutableIsRunning = MutableLiveData<Boolean>()
    val currentTime: MutableLiveData<Int> get() = mutableCurrentTime
    val isRunning: MutableLiveData<Boolean> get() = mutableIsRunning

    fun currentTime(item: Int) {
        mutableCurrentTime.value = item
    }

    fun isRunning(item: Boolean) {
        mutableIsRunning.value = item
    }
}