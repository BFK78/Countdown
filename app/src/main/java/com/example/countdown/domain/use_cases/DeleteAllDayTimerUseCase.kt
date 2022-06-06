package com.example.countdown.domain.use_cases

import com.example.countdown.domain.repository.DayTimerRepository

class DeleteAllDayTimerUseCase(
    private val repository: DayTimerRepository
) {

    suspend operator fun invoke() {
        repository.deleteAllDayTimer()
    }

}