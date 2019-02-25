package com.faltenreich.releaseradar.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat

interface Notifiable {
    val id: Int
    val context: Context
    val channel: NotifiableChannel
    val category: String?
    val title: String?
    val message: String?
    val smallIconResId: Int
    val largeIcon: Bitmap?
    val intent: Intent
    val isGroup: Boolean
    val sortKey: String?
    val localOnly: Boolean

    private val pendingIntent: PendingIntent
        get() = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val notification: Notification
        get() = context.getString(channel.idResId).let { channelId ->
            NotificationCompat.Builder(context, channelId).apply {
                setContentIntent(pendingIntent)
                priority = channel.priority
                setCategory(category)
                setGroup(channelId)
                setGroupSummary(isGroup)
                setSound(channel.soundUri)
                setAutoCancel(true)
                setSmallIcon(smallIconResId)
                setLargeIcon(largeIcon)
                setContentTitle(title)
                setContentText(message)
                setTicker(message)
                setStyle(NotificationCompat.BigTextStyle().bigText(message))
                setSortKey(sortKey)
                setLocalOnly(localOnly)
            }.build()
        }
}
