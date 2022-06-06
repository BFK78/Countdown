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
import com.example.countdown.R
import com.example.countdown.common.constants.Constants.CODE_ACHIEVE_INTENT
import com.example.countdown.common.constants.Constants.CODE_FOREGROUND_SERVICE
import com.example.countdown.common.constants.Constants.CODE_REPLY_INTENT
import com.example.countdown.common.constants.Constants.INTENT_COMMAND
import com.example.countdown.common.constants.Constants.INTENT_COMMAND_ACHIEVE
import com.example.countdown.common.constants.Constants.INTENT_COMMAND_EXIT
import com.example.countdown.common.constants.Constants.INTENT_COMMAND_REPLY
import com.example.countdown.common.constants.Constants.NOTIFICATION_CHANNEL_GENERAL
import java.lang.Exception
import java.util.*

class CountDownService: Service() {


    companion object {
        private const val TAG = "BroadcastService"
        const val COUNTDOWN_BR ="com.example.countdown"
    }

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

//        val command = intent?.getStringExtra(INTENT_COMMAND)
//
//        if (command == INTENT_COMMAND_EXIT) {
//            stopService()
//            return START_NOT_STICKY
//        }
//
//        showNotification()
//
//        if (command == INTENT_COMMAND_REPLY) {
//            Toast.makeText(this, "Clicked in the notification", Toast.LENGTH_LONG).show()
//        }

        val currentTIme = intent?.getIntExtra("", 0)

        val timer: Timer = Timer()

        timer.scheduleAtFixedRate( object : TimerTask() {

            override fun run() {

            }

        }, 0, 1000)

        return START_STICKY
    }

    private fun stopService() {
        stopForeground(true)
        stopSelf()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    private fun showNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val replyIntent = Intent(this, CountDownService::class.java).apply {
            putExtra(INTENT_COMMAND, INTENT_COMMAND_REPLY)
        }
        val achieveIntent = Intent(
            this, CountDownService::class.java
        ).apply {
            putExtra(INTENT_COMMAND, INTENT_COMMAND_ACHIEVE)
        }

        val replyPendingIntent = PendingIntent.getService(
            this, CODE_REPLY_INTENT, replyIntent, 0
        )

        val achievePendingIntent = PendingIntent.getService(
            this, CODE_ACHIEVE_INTENT, achieveIntent, 0
        )

        try {
            with(
                NotificationChannel(
                    NOTIFICATION_CHANNEL_GENERAL,
                    "Count Down",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            ) {
                enableLights(true)
                setShowBadge(true)
                enableVibration(false)
                setSound(null, null)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                manager.createNotificationChannel(this)
            }
        } catch (e: Exception) {
            Log.i("basim notification error", "${e.localizedMessage} error")
        }

        with(
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_GENERAL)
        ) {
            setTicker("basim")
            setContentTitle("Countdown")
            setContentText("Successfully using......")
            setAutoCancel(false)
            setOngoing(true)
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_baseline_play_arrow_24)
            priority = Notification.PRIORITY_MAX
            setContentIntent(replyPendingIntent)
            addAction(
                0, "REPLY", replyPendingIntent
            )
            addAction(
                0, "ACHIEVE", achievePendingIntent
            )
            startForeground(CODE_FOREGROUND_SERVICE, build())
        }
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }


}