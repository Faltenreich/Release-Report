package com.faltenreich.releaseradar.reminder.notification

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.ui.activity.MainActivity

data class Notification(
    override val id: Int,
    override val context: Context,
    override val channel: NotificationChannel,
    override val category: String?,
    override val title: String?,
    override val message: String? = null,
    override val smallIconRes: Int = R.drawable.ic_notification,
    override val largeIcon: Bitmap? = BitmapFactory.decodeResource(context.resources, smallIconRes),
    override val intent: Intent = Intent(context, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP },
    override val isGroup: Boolean = false,
    override val sortKey: String? = null,
    override val localOnly: Boolean = true
) : Notifiable