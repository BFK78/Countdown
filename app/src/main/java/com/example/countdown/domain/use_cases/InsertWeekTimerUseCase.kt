package com.example.countdown.domain.use_cases

import com.example.countdown.domain.model.WeekModel
import com.example.countdown.domain.repository.WeekTimerRepository

class InsertWeekTimerUseCase(
    private val weekTimerRepository: WeekTimerRepository
) {

    suspend operator fun invoke(weekModel: WeekModel) {
        weekTimerRepository.insertWeekTimer(weekModel = weekModel)
    }

}