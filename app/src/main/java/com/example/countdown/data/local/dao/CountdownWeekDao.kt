package com.example.countdown.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countdown.domain.model.WeekModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CountdownWeekDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeekTimer(weekModel: WeekModel)

    @Query("SELECT * FROM WEEKMODEL")
    fun getAllWeekData(): Flow<List<WeekModel>>

    @Query("DELETE FROM WeekModel")
    suspend fun deleteAllWeekData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeekTimerList(list: List<WeekModel>)

}