package com.example.countdown.domain.use_cases

import com.example.countdown.domain.model.WeekModel
import com.example.countdown.domain.repository.WeekTimerRepository

class InsertWeekTimerListUseCase(
    private val repository: WeekTimerRepository
) {

    suspend operator fun invoke(weekModel: List<WeekModel>) {
        repository.insertWeekTimerList(list = weekModel)
    }

}