package com.example.countdown.domain.use_cases

import com.example.countdown.domain.model.DayModel
import com.example.countdown.domain.repository.DayTimerRepository

class InsertDayTimerUseCase(
    private val dayTimerRepository: DayTimerRepository
) {

    suspend operator fun invoke(dayModel: DayModel) {
        dayTimerRepository.insertDayTimer(dayModel = dayModel)
    }

}