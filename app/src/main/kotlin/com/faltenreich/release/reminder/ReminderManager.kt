package com.faltenreich.release.reminder

import android.content.Context
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.reminder.notification.Notification
import com.faltenreich.release.reminder.notification.NotificationChannel
import com.faltenreich.release.reminder.notification.NotificationManager

object ReminderManager {

    fun remind(context: Context) {
        fetchReleasesOfToday { releases -> showNotification(context, releases) }
    }

    private fun fetchReleasesOfToday(onResponse: (List<Release>) -> Unit) {
        TODO()
        /*
        ReleaseRepository.getAll(
            Query(
                orderBy = "releasedAt",
                equalTo = LocalDate.now().asString
            ), onSuccess = { releases ->
                onResponse(releases)
            }, onError = {
                onResponse(listOf())
            })
        */
    }

    private fun showNotification(context: Context, releases: List<Release>) {
        val message = context.getString(R.string.reminder_title).format(releases.size)
        val notification = Notification(
            ReminderService.SERVICE_ID,
            context,
            NotificationChannel.MAIN,
            null,
            message
        )
        NotificationManager.showNotification(notification)
    }
}