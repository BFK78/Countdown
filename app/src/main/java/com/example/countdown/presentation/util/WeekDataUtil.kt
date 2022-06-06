package com.example.countdown.presentation.util

import java.text.SimpleDateFormat
import java.util.*

fun calculateTotalMinutes(date: Date): Float {

    val timeSplit = date.toString().split(" ")[3].split(":")
    val hour = timeSplit[0].toFloat()
    val minute = timeSplit[1].toFloat()

    return (hour * 60.0 + minute).toFloat()
}

fun createDate(hour: Int, minute: Int): Date {
    return SimpleDateFormat("HH:mm").parse("$hour:$minute")
}