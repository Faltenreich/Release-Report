package com.faltenreich.release.reminder

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ReminderService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ReminderManager.remind(baseContext)
        return START_NOT_STICKY
    }

    companion object {
        const val SERVICE_ID = 1337
    }
}