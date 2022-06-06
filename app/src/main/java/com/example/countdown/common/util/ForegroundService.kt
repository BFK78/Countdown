package com.example.countdown.common.util

import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.countdown.common.constants.Constants.INTENT_COMMAND
import com.example.countdown.common.service.CountDownService

fun Context.foregroundStartService(command: String) {

    val intent = Intent(this, CountDownService::class.java)
    if (command == "Start") {
        intent.putExtra(INTENT_COMMAND, command)

        this.startForegroundService(intent)
    } else if (command == "Exit") {
        intent.putExtra(INTENT_COMMAND, command)
        this.startService(intent)
    }

}