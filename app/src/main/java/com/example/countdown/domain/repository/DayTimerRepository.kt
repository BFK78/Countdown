package com.example.countdown.domain.repository

import com.example.countdown.domain.model.DayModel
import kotlinx.coroutines.flow.Flow

interface DayTimerRepository {

    suspend fun insertDayTimer(dayModel: DayModel)

    fun getTimeIntervals(indicator: Int): Flow<List<DayModel>>

    suspend fun deleteAllDayTimer()

}