package com.faltenreich.releaseradar.notification

import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationManager {

    fun showNotification(notification: Notifiable) {
        val context = notification.context
        with(context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(notification.channel.toNotificationChannel(context))
            }
            notify(notification.id, notification.notification)
        }
    }
}
