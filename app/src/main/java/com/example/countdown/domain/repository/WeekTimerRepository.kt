package com.example.countdown.domain.repository

import com.example.countdown.domain.model.WeekModel
import kotlinx.coroutines.flow.Flow

interface WeekTimerRepository {

    suspend fun insertWeekTimer(weekModel: WeekModel)

    fun getAllWeekData(): Flow<List<WeekModel>>

    suspend fun deleteAllWeekData()

    suspend fun insertWeekTimerList(list: List<WeekModel>)

}