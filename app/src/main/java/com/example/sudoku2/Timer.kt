package com.example.sudoku2

import android.os.Handler
import android.os.Looper

class Timer(private val listener: TimerListener) {
    private val handler = Handler(Looper.getMainLooper())
    private var seconds = 0
    private var isRunning = false

    private val runnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                seconds++
                listener.onTimerUpdate(seconds)
                handler.postDelayed(this, 1000)
            }
        }
    }

    fun start() {
        if (!isRunning) {
            isRunning = true
            handler.post(runnable)
        }
    }

    fun pause() {
        isRunning = false
    }

    fun reset() {
        pause()
        seconds = 0
    }
}
