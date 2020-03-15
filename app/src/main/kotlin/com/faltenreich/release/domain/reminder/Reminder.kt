package com.faltenreich.release.domain.reminder

import android.content.Context
import android.util.Log
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.faltenreich.release.R
import com.faltenreich.release.base.date.atEndOfWeek
import com.faltenreich.release.base.date.atStartOfWeek
import com.faltenreich.release.base.date.print
import com.faltenreich.release.base.log.tag
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.preference.UserPreferences
import com.faltenreich.release.domain.reminder.notification.Notification
import com.faltenreich.release.domain.reminder.notification.NotificationChannel
import com.faltenreich.release.domain.reminder.notification.NotificationManager
import com.faltenreich.release.framework.glide.toBitmap
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

object Reminder {

    private const val ID = 1337

    private const val NOTIFICATION_ICON_SIZE_IN_PIXELS = 1024
    private const val NOTIFICATION_ICON_CORNER_RADIUS_IN_DP = 128

    fun refresh(context: Context) {
        if (UserPreferences.reminderIsEnabled) {
            enqueue(context)
        } else {
            cancel(context)
        }
    }

    private fun enqueue(context: Context) {
        val now = LocalDateTime.now()
        val today = LocalDate.now()

        val reminderTime = UserPreferences.reminderTime
        val remindToday = now.toLocalTime().isBefore(reminderTime)
        val reminderDate = if (remindToday) today else today.plusDays(1)

        val target = LocalDateTime.of(reminderDate, reminderTime)
        val delayInMillis = ChronoUnit.MILLIS.between(now, target)

        ReminderWorker.enqueue(context, delayInMillis)
        Log.d(tag, "Enqueued reminder for ${reminderTime.print()}")
    }

    private fun cancel(context: Context) {
        ReminderWorker.cancel(context)
        Log.d(tag, "Cancelled reminder")
    }

    suspend fun remind(context: Context) {
        remindAboutNextWeek(context)
        remindAboutSubscriptions(context)
    }

    private suspend fun remindAboutNextWeek(context: Context) {
        val today = LocalDate.now()
        val startOfWeek = today.atStartOfWeek
        val showReminder = today == startOfWeek
        if (showReminder) {
            val endOfWeek = today.atEndOfWeek
            val releases = ReleaseRepository.getBetween(startOfWeek, endOfWeek, 10)
            val title = context.getString(R.string.reminder_weekly_notification)
            showNotification(context, title, releases)
        }
    }

    private suspend fun remindAboutSubscriptions(context: Context) {
        val releases = ReleaseRepository.getSubscriptions(LocalDate.now())
        val title = context.getString(R.string.reminder_subscriptions_notification).format(releases.size)
        showNotification(context, title, releases)
    }

    private suspend fun showNotification(context: Context, title: String, releases: List<Release>) {
        releases.takeIf(List<*>::isNotEmpty) ?: return
        val promo = releases.first()

        val image = promo.imageUrlForThumbnail?.toBitmap(
            context,
            NOTIFICATION_ICON_SIZE_IN_PIXELS,
            RequestOptions().transform(
                CenterCrop(),
                RoundedCorners(NOTIFICATION_ICON_CORNER_RADIUS_IN_DP)
            )
        )
        val text = releases.mapNotNull(Release::titleFull).joinToString()

        val notification = Notification(
            ID,
            context,
            NotificationChannel.MAIN,
            title = title,
            text = text,
            largeIcon = image
        )
        NotificationManager.showNotification(notification)
    }
}