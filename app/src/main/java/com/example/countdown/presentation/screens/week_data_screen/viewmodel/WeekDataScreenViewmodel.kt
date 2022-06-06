package com.example.countdown.presentation.screens.week_data_screen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdown.domain.model.WeekModel
import com.example.countdown.domain.use_cases.GetAllWeekDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeekDataScreenViewModel @Inject constructor(
    private val getAllWeekDataUseCase: GetAllWeekDataUseCase
): ViewModel() {

    init {
        getAllWeekData()
    }

    private val _weekDataState = mutableStateOf(emptyList<WeekModel>())
    val weekDataState: State<List<WeekModel>> = _weekDataState


    private fun getAllWeekData() = viewModelScope.launch {
        getAllWeekDataUseCase().collectLatest {
            _weekDataState.value = it
        }
    }
}