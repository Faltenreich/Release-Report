package com.faltenreich.release.domain.reminder

import android.content.Context
import android.util.Log
import com.faltenreich.release.R
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

    private const val SERVICE_ID = 1337

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
        remindAboutFavorites(context)
    }

    private fun remindAboutFavorites(context: Context) {
        ReleaseRepository.getFavorites(LocalDate.now()) { favorites ->
            if (favorites.isNotEmpty()) {
                showNotification(context, favorites)
            }
        }
    }

    private fun showNotification(context: Context, favorites: List<Release>) {
        val message = context.getString(R.string.reminder_title).format(favorites.size)
        val printedReleases = favorites.mapNotNull { release ->
            release.title?.let { title ->
                release.artistName?.let { artistName -> "$artistName - $title" } ?: title
            }
        }
        val notification = Notification(
            SERVICE_ID,
            context,
            NotificationChannel.MAIN,
            title = message,
            message = printedReleases.joinToString(", ")
        )
        NotificationManager.showNotification(notification)
    }
}