package com.faltenreich.releaseradar

import android.app.Application
import com.faltenreich.releaseradar.data.preference.UserPreferences
import com.faltenreich.releaseradar.notification.Notification
import com.faltenreich.releaseradar.notification.NotificationAlarmReceiver
import com.faltenreich.releaseradar.notification.NotificationChannel
import com.faltenreich.releaseradar.notification.NotificationManager
import com.jakewharton.threetenabp.AndroidThreeTen

@Suppress("unused")
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        AndroidThreeTen.init(this)
        UserPreferences.init(this)

        val notification = Notification(NotificationAlarmReceiver.SERVICE_ID, this, NotificationChannel.MAIN, null, "Test")
        NotificationManager.showNotification(notification)
    }
}