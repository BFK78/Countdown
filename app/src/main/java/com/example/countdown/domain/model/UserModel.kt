package com.example.countdown.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name: String,
    val wake_up: Date,
    val go_down: Date
)