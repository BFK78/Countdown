package com.example.countdown.common.util

import com.example.countdown.domain.model.DayModel
import java.text.SimpleDateFormat
import java.util.*

fun List<DayModel>.addDate(): Date {

    var date = SimpleDateFormat("HH:mm").parse("00:00")

    this.forEach {
        date = com.example.countdown.presentation.util.addDate(date, it.time)
    }

    return date!!
}






















































