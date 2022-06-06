package com.example.countdown.data.local.repository

import android.util.Log
import com.example.countdown.data.local.dao.CountdownWeekDao
import com.example.countdown.domain.model.WeekModel
import com.example.countdown.domain.repository.WeekTimerRepository
import kotlinx.coroutines.flow.Flow

class WeekTimerRepositoryImplementation(
    private val weekDao: CountdownWeekDao
): WeekTimerRepository {

    override suspend fun insertWeekTimer(weekModel: WeekModel) {
        Log.i("basimrepo", "called")
        return weekDao.insertWeekTimer(weekModel = weekModel)
    }

    override fun getAllWeekData(): Flow<List<WeekModel>> {
        return weekDao.getAllWeekData()
    }

    override suspend fun deleteAllWeekData() {
        weekDao.deleteAllWeekData()
    }

    override suspend fun insertWeekTimerList(list: List<WeekModel>) {
        Log.i("basimrepolist", "called")
        weekDao.insertWeekTimerList(list = list)
    }

}