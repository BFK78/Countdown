package com.example.countdown.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuotesModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val content: String,
    val author: String
)
