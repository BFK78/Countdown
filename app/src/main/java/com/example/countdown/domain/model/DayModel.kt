package com.example.countdown.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DayModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val start_time: Date,
    val end_time: Date,
    val time: Date,
    val time_indicator: Int,
    val date: Date
)
