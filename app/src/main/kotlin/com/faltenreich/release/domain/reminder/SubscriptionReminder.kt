package com.faltenreich.release.domain.reminder

import android.content.Context
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.reminder.notification.Notification
import com.faltenreich.release.domain.reminder.notification.NotificationChannel
import com.faltenreich.release.domain.reminder.notification.NotificationManager
import org.threeten.bp.LocalDate

class SubscriptionReminder {

    fun remind(context: Context) {
        ReleaseRepository.getSubscriptions(LocalDate.now()) { subscriptions ->
            if (subscriptions.isNotEmpty()) {
                showNotification(context, subscriptions)
            }
        }
    }

    private fun showNotification(context: Context, subscriptions: List<Release>) {
        val message = context.getString(R.string.reminder_title).format(subscriptions.size)
        val printedReleases = subscriptions.mapNotNull { release ->
            release.title?.let { title ->
                release.artistName?.let { artistName -> "$artistName - $title" } ?: title
            }
        }
        val notification = Notification(
            ID,
            context,
            NotificationChannel.MAIN,
            title = message,
            message = printedReleases.joinToString(", ")
        )
        NotificationManager.showNotification(notification)
    }

    companion object {
        private const val ID = 1337
    }
}