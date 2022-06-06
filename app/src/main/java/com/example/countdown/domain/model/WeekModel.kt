package com.example.countdown.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class WeekModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val successful_time: Date,
    val unsuccessful_time: Date,
    val day: String,
    val date: Date
)
