package com.example.countdown.domain.use_cases

import com.example.countdown.domain.model.WeekModel
import com.example.countdown.domain.repository.WeekTimerRepository
import kotlinx.coroutines.flow.Flow

class GetAllWeekDataUseCase(
    private val repository: WeekTimerRepository
) {

    operator fun invoke(): Flow<List<WeekModel>> {
        return repository.getAllWeekData()
    }

}