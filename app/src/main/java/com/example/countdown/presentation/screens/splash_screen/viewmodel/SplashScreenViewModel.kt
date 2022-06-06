package com.example.countdown.presentation.screens.splash_screen.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdown.common.util.addDate
import com.example.countdown.domain.model.DayModel
import com.example.countdown.domain.model.WeekModel
import com.example.countdown.domain.use_cases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val deleteAllDayTimerUseCase: DeleteAllDayTimerUseCase,
    private val getTimeIntervalUseCase: GetTimeIntervalUseCase,
    private val insertWeekTimerUseCase: InsertWeekTimerUseCase,
    private val deleteAllWeekTimerListUseCase: DeleteAllWeekTimerListUseCase,
    private val insertWeekTimerListUseCase: InsertWeekTimerListUseCase,
    private val getAllWeekDataUseCase: GetAllWeekDataUseCase
): ViewModel() {

    private val _weekDataState = mutableStateOf(emptyList<WeekModel>())
    val weekDataState: State<List<WeekModel>> = _weekDataState

    private val _wastedTimerState = mutableStateOf(emptyList<DayModel>())
    private val wastedTimerState: State<List<DayModel>> = _wastedTimerState

    private val _successfulTimerState = mutableStateOf(emptyList<DayModel>())
    private val successfulTimerState: State<List<DayModel>> = _successfulTimerState

    init {
        getAllWeekData()
    }

    fun deleteAllDayTimer() = viewModelScope.launch {
        deleteAllDayTimerUseCase.invoke()
    }

    fun getSuccessfulTimer() = viewModelScope.launch {
        getTimeIntervalUseCase(indicator = 1).collectLatest {
            _successfulTimerState.value = it
            getUnsuccessfulTimer()
        }
    }

    private fun getAllWeekData() = viewModelScope.launch {
        getAllWeekDataUseCase().collectLatest {
            _weekDataState.value = it
        }
    }

    private fun getUnsuccessfulTimer() = viewModelScope.launch {
        getTimeIntervalUseCase(indicator = 0).collectLatest {
            _wastedTimerState.value = it
            insertWeekTimerState()
        }
    }

    private fun insertWeekTimerState() = viewModelScope.launch {

        Log.i("basimreviewinser", "called")

        val sumSuccessfulDate = successfulTimerState.value.addDate()

        val sumWastedDate = wastedTimerState.value.addDate()

        if (successfulTimerState.value.isNotEmpty()) {

            val weekModel = WeekModel(
                successful_time = sumSuccessfulDate,
                unsuccessful_time = sumWastedDate,
                day = successfulTimerState.value[0].date.toString().split(" ")[0],
                date = successfulTimerState.value[0].date
            )

            insertWeekTimerUseCase(weekModel = weekModel)
        }

    }

    fun updateWeekData() = viewModelScope.launch {

        Log.i("basimreviewup", "called")

        val sumSuccessfulDate = successfulTimerState.value.addDate()

        val sumWastedDate = wastedTimerState.value.addDate()

        val weekModel = WeekModel(
            successful_time = sumSuccessfulDate,
            unsuccessful_time = sumWastedDate,
            day = successfulTimerState.value[0].date.toString().split(" ")[0],
            date = successfulTimerState.value[0].date
        )

        getAllWeekDataUseCase().collectLatest {
            val list = it.toMutableList()
            list.removeAt(0)
            list.add(weekModel)
            deleteAllWeekTimerListUseCase()
            insertWeekTimerListUseCase(weekModel = list)
        }

    }

}