package com.example.countdown.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.countdown.data.local.dao.CountdownDayDao
import com.example.countdown.data.local.dao.CountdownUserDao
import com.example.countdown.data.local.dao.CountdownWeekDao
import com.example.countdown.data.local.dao.QuotesDao
import com.example.countdown.data.local.typeconverter.DateConverter
import com.example.countdown.domain.model.DayModel
import com.example.countdown.domain.model.QuotesModel
import com.example.countdown.domain.model.UserModel
import com.example.countdown.domain.model.WeekModel

@Database(
    entities = [DayModel::class, UserModel::class, WeekModel::class, QuotesModel::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class CountdownDatabase: RoomDatabase() {

    abstract val userDao: CountdownUserDao

    abstract val dayDao: CountdownDayDao

    abstract val weekDao: CountdownWeekDao

    abstract val quotesDao: QuotesDao

}