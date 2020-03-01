package com.faltenreich.release.domain.reminder.notification

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.faltenreich.release.R
import com.faltenreich.release.domain.navigation.MainActivity

data class Notification(
    override val id: Int,
    override val context: Context,
    override val channel: NotificationChannel,
    override val category: String? = null,
    override val title: String,
    override val message: String? = null,
    override val smallIconRes: Int = R.drawable.ic_notification,
    override val largeIcon: Bitmap? = null,
    override val intent: Intent = Intent(context, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP },
    override val isGroup: Boolean = false,
    override val sortKey: String? = null,
    override val localOnly: Boolean = true,
    override val style: NotificationCompat.Style? = null,
    override val color: Int = ContextCompat.getColor(context, R.color.colorPrimary)
) : Notifiable