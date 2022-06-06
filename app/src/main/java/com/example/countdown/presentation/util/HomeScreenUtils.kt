package com.example.countdown.presentation.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun calculateDay(day: Int): String {
    when(day) {

        Calendar.SUNDAY -> {
            return "Sun"
        }

        Calendar.MONDAY -> {
            return "Mon"
        }

        Calendar.TUESDAY -> {
            return "Tue"
        }

        Calendar.WEDNESDAY -> {
            return "Wen"
        }

        Calendar.THURSDAY -> {
            return "Thu"
        }

        Calendar.FRIDAY -> {
            return "Fri"
        }

        Calendar.SATURDAY -> {
            return "Sat"
        }

        else -> {
            return ""
        }
    }
}

fun incrementTime(mh: Int, mt: Int, sh: Int, st: Int, hh: Int, ht: Int): String {

    var minutesH = mh
    var minutesT = mt
    var secondsH = sh
    var secondsT = st
    var hourH = hh
    var hourT = ht

    var minute = ""
    var second = ""
    var hour = ""

    secondsH++

    minute = "$minutesT$minutesH"

    hour = "$hourT$hourH"

    if (secondsH < 60) {

        second = "$secondsT$secondsH"

        if (secondsH > 9) {
            second = "$secondsH"
        }

    } else {
        second = "00"
        minutesH++
        if (minutesH < 60) {
            minute = "$minutesT$minutesH"
            if (minutesH > 9) {
                minute = "$minutesH"
            }
        } else {
            hourH++
            if (hourH > 9) {
                hour = "$hourH"
            }
        }
    }


    return "$hour : $minute : $second"
}

fun calculateDifferenceBetweenTime(date1: Date, date2: Date): Date {

    val timeSplit1 = date1.toString().split(" ")[3].split(":")
    val timeSplit2 = date2.toString().split(" ")[3].split(":")

    val hour1 = timeSplit1[0]
    val hour2 = timeSplit2[0]
    val minute1 = timeSplit1[1]
    val minute2 = timeSplit2[1]

    val totalMinutes1 = hour1.toInt() * 60 + minute1.toInt()
    val totalMinutes2 = hour2.toInt() * 60 + minute2.toInt()



    val differenceInMinute = totalMinutes1 - totalMinutes2

    val cHour = (differenceInMinute/60)
    val cMinutes = differenceInMinute % 60

    return  SimpleDateFormat("HH : mm").parse("${abs(cHour)} : ${abs(cMinutes)}")
}

fun addDate(date1: Date, date2: Date): Date {

    val timeSplit1 = date1.toString().split(" ")[3].split(":")
    val timeSplit2 = date2.toString().split(" ")[3].split(":")

    val hour1 = timeSplit1[0]
    val hour2 = timeSplit2[0]
    val minute1 = timeSplit1[1]
    val minute2 = timeSplit2[1]

    val totalMinutes1 = hour1.toInt() * 60 + minute1.toInt()
    val totalMinutes2 = hour2.toInt() * 60 + minute2.toInt()



    val differenceInMinute = totalMinutes1 + totalMinutes2

    val cHour = (differenceInMinute/60)
    val cMinutes = differenceInMinute % 60

    return  SimpleDateFormat("HH : mm").parse("${abs(cHour)} : ${abs(cMinutes)}")
}



