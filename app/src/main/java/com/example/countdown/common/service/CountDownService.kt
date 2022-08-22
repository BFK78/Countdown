package com.example.countdown.common.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.countdown.MainActivity
import com.example.countdown.R
import com.example.countdown.common.constants.Constants.CODE_ACHIEVE_INTENT
import com.example.countdown.common.constants.Constants.CODE_FOREGROUND_SERVICE
import com.example.countdown.common.constants.Constants.CODE_REPLY_INTENT
import com.example.countdown.common.constants.Constants.FROM_SERVICE_INTENT_EXTRA
import com.example.countdown.common.constants.Constants.INTENT_COMMAND
import com.example.countdown.common.constants.Constants.INTENT_COMMAND_ACHIEVE
import com.example.countdown.common.constants.Constants.INTENT_COMMAND_EXIT
import com.example.countdown.common.constants.Constants.INTENT_COMMAND_REPLY
import com.example.countdown.common.constants.Constants.NOTIFICATION_CHANNEL_GENERAL
import com.example.countdown.common.constants.Constants.NOTIFICATION_CHANNEL_ID
import com.example.countdown.common.constants.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.countdown.common.constants.Constants.SERVICE_INTENT_EXTRA
import java.lang.Exception
import java.util.*

class CountDownService: Service() {


    companion object {
        private const val TAG = "BroadcastService"
        const val COUNTDOWN_BR ="com.example.countdown"
    }

    private val timer = Timer()

    val intent = Intent(COUNTDOWN_BR)
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate() {
        super.onCreate()
        countDownTimer = object:  CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                intent.putExtra("countdown", p0)
                sendBroadcast(intent)
            }
            override fun onFinish() {
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var currentTime = intent?.getIntExtra(SERVICE_INTENT_EXTRA, 0)

        timer.scheduleAtFixedRate( object : TimerTask() {
            override fun run() {

                val intentLocal = Intent()
                intentLocal.setAction("Counter")

                currentTime = currentTime!! + 1

//                notificationUpdate(currentTime!!)

                intentLocal.putExtra(FROM_SERVICE_INTENT_EXTRA, currentTime)
                sendBroadcast(intentLocal)
            }

        }, 1000, 1000)

        return START_STICKY
    }



    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        Log.i("basu", "Destroyed called")
        timer.cancel()
        super.onDestroy()
    }


}