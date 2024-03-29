package com.faltenreich.release.domain.reminder.notification

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import com.faltenreich.release.R
import com.faltenreich.release.domain.navigation.MainActivity
import com.faltenreich.release.framework.android.context.getColorFromAttribute

data class Notification(
    override val id: Int,
    override val context: Context,
    override val channel: NotificationChannel,
    override val category: String? = null,
    override val title: String,
    override val text: String? = null,
    override val smallIconRes: Int = R.drawable.ic_notification,
    override val largeIcon: Bitmap? = null,
    override val intent: Intent = Intent(context, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP },
    override val isGroup: Boolean = false,
    override val sortKey: String? = null,
    override val localOnly: Boolean = true,
    override val style: NotificationCompat.Style? = null,
    override val color: Int = context.getColorFromAttribute(android.R.attr.colorPrimary)
) : Notifiable