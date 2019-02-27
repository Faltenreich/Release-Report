package com.faltenreich.releaseradar.notification

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.faltenreich.releaseradar.extension.className

class NotificationAlarmReceiver : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(className, "Starting service from alarm")
        notifyReleases(baseContext)
        return START_NOT_STICKY
    }

    private fun notifyReleases(context: Context) {
        val notification = Notification(SERVICE_ID, context, NotificationChannel.MAIN, null, "Test")
        NotificationManager.showNotification(notification)
    }

    companion object {
        const val SERVICE_ID = 1337
    }
}