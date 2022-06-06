package com.example.countdown.data.local.dao

import androidx.room.*
import com.example.countdown.domain.model.DayModel
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CountdownDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayTimer(dayModel: DayModel)

    @Query("SELECT * FROM DAYMODEL WHERE time_indicator = :indicator")
    fun getTimeIntervals(indicator: Int): Flow<List<DayModel>>

    @Query("DELETE FROM DAYMODEL")
    suspend fun deleteAllDayTimer()

}