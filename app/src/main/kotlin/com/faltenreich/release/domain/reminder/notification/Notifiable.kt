package com.faltenreich.release.domain.reminder.notification

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
    val text: String?
    val smallIconRes: Int
    val largeIcon: Bitmap?
    val intent: Intent
    val isGroup: Boolean
    val sortKey: String?
    val localOnly: Boolean
    val style: NotificationCompat.Style?
    val color: Int

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
                setSmallIcon(smallIconRes)
                setLargeIcon(largeIcon)
                setContentTitle(title)
                setContentText(text)
                setTicker(text)
                setStyle(style)
                setSortKey(sortKey)
                setLocalOnly(localOnly)
                color = this@Notifiable.color
            }.build()
        }
}
