package com.example.countdown.data.local.repository

import com.example.countdown.data.local.dao.CountdownDayDao
import com.example.countdown.domain.model.DayModel
import com.example.countdown.domain.repository.DayTimerRepository
import kotlinx.coroutines.flow.Flow

class DayTimerRepositoryImplementation(
    private val dayDao: CountdownDayDao
): DayTimerRepository {

    override suspend fun insertDayTimer(dayModel: DayModel) {
        dayDao.insertDayTimer(dayModel = dayModel)
    }

    override fun getTimeIntervals(indicator: Int): Flow<List<DayModel>> {
        return dayDao.getTimeIntervals(indicator = indicator)
    }

    override suspend fun deleteAllDayTimer() {
        dayDao.deleteAllDayTimer()
    }

}