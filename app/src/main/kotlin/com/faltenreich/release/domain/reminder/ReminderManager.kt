package com.faltenreich.release.domain.reminder

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.reminder.notification.Notification
import com.faltenreich.release.domain.reminder.notification.NotificationChannel
import com.faltenreich.release.domain.reminder.notification.NotificationManager
import java.util.concurrent.TimeUnit

object ReminderManager {

    private const val SERVICE_ID = 1337

    fun init(context: Context) {
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(context).enqueue(request)
    }

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
            SERVICE_ID,
            context,
            NotificationChannel.MAIN,
            null,
            message
        )
        NotificationManager.showNotification(notification)
    }
}