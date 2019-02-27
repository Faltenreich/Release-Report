package com.faltenreich.releaseradar.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NotificationAlarmReceiver : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notifyReleases()
        return START_NOT_STICKY
    }

    private fun notifyReleases() {
        // TODO
    }
}