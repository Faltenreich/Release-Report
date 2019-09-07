package com.faltenreich.release.domain.reminder

import android.content.Context
import android.util.Log
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
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

object Reminder {

    private const val ID = 1337
    private const val NOTIFICATION_MAXIMUM_RELEASE_COUNT = 3
    private const val NOTIFICATION_DELIMITER = ", "

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

    fun remind(context: Context) {
        remindAboutNextWeek(context)
        remindAboutSubscriptions(context)
    }

    private fun remindAboutNextWeek(context: Context) {
        val today = LocalDate.now()
        val showReminder = today == today.atEndOfWeek
        if (true) {
            val nextWeek = today.plusWeeks(1)
            val (start, end) = nextWeek.atStartOfWeek to nextWeek.atEndOfWeek
            ReleaseRepository.getBetween(start, end) { releases ->
                val title = context.getString(R.string.reminder_weekly_notification)
                showNotification(context, title, releases)

            }
        }
    }

    private fun remindAboutSubscriptions(context: Context) {
        ReleaseRepository.getSubscriptions(LocalDate.now()) { releases ->
            if (releases.isNotEmpty()) {
                val title = context.getString(R.string.reminder_subscriptions_notification).format(releases.size)
                showNotification(context, title, releases)
            }
        }
    }

    private fun showNotification(context: Context, title: String, releases: List<Release>) {
        val message = releases.take(NOTIFICATION_MAXIMUM_RELEASE_COUNT).mapNotNull { release ->
            release.title?.let { title ->
                release.artistName?.let { artistName -> "$artistName - $title" } ?: title
            }
        }.joinToString(NOTIFICATION_DELIMITER).plus(if (releases.size > NOTIFICATION_MAXIMUM_RELEASE_COUNT) "$NOTIFICATION_DELIMITER..." else null)
        val notification = Notification(
            ID,
            context,
            NotificationChannel.MAIN,
            title = title,
            message = message
        )
        NotificationManager.showNotification(notification)
    }
}