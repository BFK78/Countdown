package com.example.countdown.domain.use_cases

import com.example.countdown.domain.model.DayModel
import com.example.countdown.domain.repository.DayTimerRepository
import kotlinx.coroutines.flow.Flow


class GetTimeIntervalUseCase(
    private val repository: DayTimerRepository
) {

    operator fun invoke(indicator: Int): Flow<List<DayModel>> {
        return repository.getTimeIntervals(indicator = indicator)
    }

}