package com.faltenreich.releaseradar.notification

import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationManager {

    private fun getNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    // Creates notification channel
    fun ensureNotificationChannel(context: Context, channel: NotifiableChannel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager(context).createNotificationChannel(channel.toNotificationChannel(context))
        }
    }

    fun ensureNotificationChannel(notification: Notifiable) {
        ensureNotificationChannel(notification.context, notification.channel)
    }

    // Show local notification (after ensuring its notification channel on Android 8+)
    fun showNotification(notification: Notifiable) {
        ensureNotificationChannel(notification)
        getNotificationManager(notification.context).notify(notification.id, notification.notification)
    }

    fun clearNotifications(context: Context) {
        getNotificationManager(context).cancelAll()
    }
}
