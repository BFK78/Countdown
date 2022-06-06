package com.example.countdown.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.countdown.common.constants.Constants.COUNTDOWN_DATASTORE
import com.example.countdown.common.constants.Constants.COUNTDOWN_ON_BOARDING
import com.example.countdown.common.constants.Constants.DAY_TIME
import com.example.countdown.common.constants.Constants.END_TIME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

val Context.datastore: DataStore<Preferences> by preferencesDataStore(COUNTDOWN_DATASTORE) //Defining the datastore
val ON_BOARDING_KEY = booleanPreferencesKey(COUNTDOWN_ON_BOARDING) //creating a key value
val END_TIME_KEY = stringPreferencesKey(END_TIME)
val DAY_TIME_KEY = stringPreferencesKey(DAY_TIME)

suspend fun updateShownBoardingScreen(context: Context) { //add data to datastore
    context.datastore.edit { settings ->
        settings[ON_BOARDING_KEY] = true
    }
}

fun getShownBoardingScreen(context: Context): Flow<Boolean> { //retrieving the data from the datastore
    return context.datastore.data
        .map { //returns a flow with the results
            it[ON_BOARDING_KEY] ?: false
        }
}

suspend fun updateEndTime(context: Context, endTime: Date) {
    context.datastore.edit { settings ->
        settings[END_TIME_KEY] = endTime.toString()
    }
}

fun getEndTime(context: Context): Flow<String> {
    return context.datastore.data
        .map {
            it[END_TIME_KEY] ?: Date().toString()
        }
}

suspend fun updateTime(context: Context, date: Date) {
    context.datastore.edit { settings ->
        settings[DAY_TIME_KEY] = date.toString()
    }
}

fun getDayTime(context: Context): Flow<String> {
    return context.datastore.data
        .map {
            it[DAY_TIME_KEY] ?: "MON 27 APR 11:32:34"
        }
}