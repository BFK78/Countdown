package com.example.countdown.presentation.screens.day_detail_screen.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdown.domain.model.DayModel
import com.example.countdown.domain.model.UserModel
import com.example.countdown.domain.use_cases.GetTimeIntervalUseCase
import com.example.countdown.domain.use_cases.UserUseCase
import com.example.countdown.presentation.util.calculateDifferenceBetweenTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DailyDetailViewModel @Inject constructor(
    private val getUserUseCase: UserUseCase,
    private val getTimeIntervalUseCase: GetTimeIntervalUseCase

) : ViewModel() {

    private val _totalTimeDateState = mutableStateOf(Date())
    val totalTimeDateState: State<Date> = _totalTimeDateState

    private val _totalTimeState = mutableStateOf("")
    val totalTimeState: State<String> = _totalTimeState

    private val _state = mutableStateOf<UserModel?>(null)
    val state: State<UserModel?> = _state

    private val _successfulTimerState = mutableStateOf<List<DayModel>>(emptyList())
    val successfulTimerState: State<List<DayModel>> = _successfulTimerState

    private val _wastefulTimerState = mutableStateOf<List<DayModel>>(emptyList())
    val wastefulTimerState: State<List<DayModel>> = _wastefulTimerState

    private fun getUser() =  viewModelScope.launch {
        delay(1000)
        getUserUseCase.getUser().collectLatest {
            _state.value = it
            calculateTotalTime(it.wake_up, it.go_down)
        }
    }

    init {
        getDailyTimer(1)
        getDailyTimer(0)
        getUser()
    }

    private fun getDailyTimer(indicator: Int) = viewModelScope.launch {
        getTimeIntervalUseCase(indicator = indicator).collectLatest {
            if (indicator == 0) {
                if (it.isNotEmpty()) {
                    _wastefulTimerState.value = it
                }
            } else {
                if (it.isNotEmpty()) {
                    _successfulTimerState.value = it
                }
            }
        }
    }

    private fun calculateTotalTime(wakeUpTime: Date, sleepTime: Date) {
        _totalTimeDateState.value = calculateDifferenceBetweenTime(sleepTime, wakeUpTime)
        _totalTimeState.value =  calculateDifferenceBetweenTime(sleepTime, wakeUpTime).toString().split(" ")[3].split(":")[0]
    }

    fun calculateTimePercentage(totalTime: Date, uwTime: Date): String {
        val timeSplit1 = totalTime.toString().split(" ")[3].split(":")
        val timeSplit2 = uwTime.toString().split(" ")[3].split(":")

        val hour1 = timeSplit1[0]
        val hour2 = timeSplit2[0]
        val minute1 = timeSplit1[1]
        val minute2 = timeSplit2[1]

        val totalMinutes1 = hour1.toInt() * 60 + minute1.toInt()
        val totalMinutes2 = hour2.toInt() * 60 + minute2.toInt()

        val percentage = (totalMinutes2.toFloat()/totalMinutes1.toFloat()) * 100

        return "${percentage.toInt()}%"
    }
}