package com.faltenreich.release.domain.reminder

import android.content.Context
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.reminder.notification.Notification
import com.faltenreich.release.domain.reminder.notification.NotificationChannel
import com.faltenreich.release.domain.reminder.notification.NotificationManager
import org.threeten.bp.LocalDate

object ReminderManager {

    private const val SERVICE_ID = 1337

    fun init(context: Context) {
        ReminderWorker.enqueue(context)
    }

    fun remind(context: Context) {
        ReleaseRepository.getFavorites(LocalDate.now()) { favorites ->
            showNotification(context, favorites)
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