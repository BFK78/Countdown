package com.example.countdown.presentation.screens.home_screen.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdown.common.util.Resources
import com.example.countdown.domain.model.DayModel
import com.example.countdown.domain.model.UserModel
import com.example.countdown.domain.use_cases.*
import com.example.countdown.presentation.screens.home_screen.state.QuoteState
import com.example.countdown.presentation.util.calculateDifferenceBetweenTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val insertDayTimerUseCase: InsertDayTimerUseCase,
    private val getTimeIntervalUseCase: GetTimeIntervalUseCase,
    private val getQuoteUseCase: GetQuoteUseCase,
    private val getUserUseCase: UserUseCase
): ViewModel() {

    private val _userState = mutableStateOf<UserModel?>(null)
    val userState: State<UserModel?> = _userState

    private val _state = mutableStateOf(QuoteState())
    val state: State<QuoteState> = _state

    private val _dayTimerState = mutableStateOf(emptyList<DayModel>())
    val dayTimerState: State<List<DayModel>> = _dayTimerState

    private val _successfulTimeState = mutableStateOf(Date())
    val successfulTimeState: State<Date> = _successfulTimeState

    private val _wastefulTimeState = mutableStateOf(Date())
    val wastefulTimeState: State<Date> = _wastefulTimeState

    init {
        getTimerIntervals(1)
        getUserModel()
        getQuote()
    }

    private fun getQuote() = viewModelScope.launch {
        getQuoteUseCase().onEach {
            when(it) {

                is Resources.Loading -> {
                    if (_state.value.isLoading) {
                        _state.value = state.value.copy(
                            isLoading = true
                        )
                    }
                }

                is Resources.Success -> {
                    _state.value = state.value.copy(
                        quote = it.data,
                        isLoading = false
                    )
                }

                is Resources.Error -> {
                    _state.value = state.value.copy(
                        quote = it.data,
                        isLoading = false
                    )
                }

            }
        }.launchIn(this)
    }

    private fun getUserModel() = viewModelScope.launch {
        getUserUseCase.getUser().collectLatest {

                _userState.value = it

        }
    }

    fun insertDayTimer(dayModel: DayModel) = viewModelScope.launch {
        insertDayTimerUseCase.invoke(dayModel = dayModel)
    }

    private fun getTimerIntervals(indicator: Int) = viewModelScope.launch {
        getTimeIntervalUseCase(indicator = indicator).collectLatest { list ->
            _dayTimerState.value = list

            calculateSuccessfulTime(list).collectLatest { date ->
                _successfulTimeState.value = date
            }

            calculateWastefulTime().collectLatest { date ->
                _wastefulTimeState.value = date
            }
        }
    }


    private fun calculateSuccessfulTime(list: List<DayModel>) = flow<Date> {
        val calendar = Calendar.getInstance()
        val cTotal = calendar.clone() as Calendar

        if (list.isNotEmpty()) {
            cTotal.time = list[0].time

            for (i in 1 until list.size) {
                calendar.time = list[i].time
                cTotal.add(Calendar.MINUTE, calendar.get(Calendar.MINUTE))
                cTotal.add(Calendar.SECOND, calendar.get(Calendar.SECOND))
                cTotal.add(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY))
            }
        }

         emit(cTotal.time)
    }

    fun calculateWastefulTime() = flow {

        val wakeUp = userState.value?.wake_up
        while (wakeUp != null) {
            val currentDate = Date()
            val untilTime = calculateDifferenceBetweenTime(currentDate, wakeUp)
            val successfulTime = successfulTimeState.value
            val wastefulTime = calculateDifferenceBetweenTime(date1 = untilTime, date2 = successfulTime)
            emit(wastefulTime)
            delay(1000)
        }
    }


}