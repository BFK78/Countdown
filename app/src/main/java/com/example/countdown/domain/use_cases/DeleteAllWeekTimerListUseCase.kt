package com.example.countdown.domain.use_cases

import com.example.countdown.domain.repository.WeekTimerRepository

class DeleteAllWeekTimerListUseCase(
    private val repository: WeekTimerRepository
) {

    suspend operator fun invoke() {
        repository.deleteAllWeekData()
    }

}