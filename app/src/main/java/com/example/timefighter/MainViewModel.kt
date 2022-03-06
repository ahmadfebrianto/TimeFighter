package com.example.timefighter

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    companion object {
        const val COUNTDOWN_INTERVAL: Long = 1000
        const val INITIAL_COUNTDOWN: Long = 60000
    }

    private var _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private var _timeLeft = MutableLiveData(INITIAL_COUNTDOWN / COUNTDOWN_INTERVAL)
    val timeLeft: LiveData<Long> = _timeLeft

    private var _isGameStarted = MutableLiveData(false)
    val isGameStarted: LiveData<Boolean> = _isGameStarted

    private var _isTimeUp = MutableLiveData(false)
    val isTimeUp: LiveData<Boolean> = _isTimeUp

    private val countDownTimer = object : CountDownTimer(INITIAL_COUNTDOWN, COUNTDOWN_INTERVAL) {
        override fun onTick(milliSeconds: Long) {
            _timeLeft.value = milliSeconds / COUNTDOWN_INTERVAL
        }

        override fun onFinish() {
            _isTimeUp.value = true
        }

    }

    fun startGame() {
        countDownTimer.start()
        _isGameStarted.value = true
        _isTimeUp.value = false
    }

    fun endGame() {
        _score.value = 0
        _timeLeft.value = INITIAL_COUNTDOWN / COUNTDOWN_INTERVAL
        _isGameStarted.value = false
    }

    fun incrementScore() {
        _score.value = _score.value?.plus(1)
    }

}