package com.faltenreich.releaseradar.notification

import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.faltenreich.releaseradar.R

enum class NotificationChannel (
    @StringRes override val idResId: Int,
    @StringRes override val nameResId: Int,
    override val priority: Int,
    override val importance: Int,
    override val soundUri: Uri? = Settings.System.DEFAULT_NOTIFICATION_URI,
    override val showBadge: Boolean = true
) : NotifiableChannel {
    // Notification channels are sorted alphabetically by id per system default
    MAIN(R.string.notification_channel_main, R.string.notifications, NotificationCompat.PRIORITY_HIGH, NotificationManagerCompat.IMPORTANCE_HIGH)
}
