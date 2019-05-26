package com.faltenreich.release.reminder.notification

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
interface NotifiableChannel {
    val idResId: Int
    val nameResId: Int
    val priority: Int
    val importance: Int
    val soundUri: Uri?
    val showBadge: Boolean

    fun toNotificationChannel(context: Context): NotificationChannel = NotificationChannel(context.getString(idResId), context.getString(nameResId), importance).apply {
        lightColor = Color.RED
        enableVibration(true)
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        setSound(soundUri, null)
        setShowBadge(showBadge)
    }
}
