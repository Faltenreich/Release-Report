package com.faltenreich.release.domain.reminder

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.TimeUnit

class ReminderWorker(
    applicationContext: Context,
    params: WorkerParameters
) : CoroutineWorker(applicationContext, params) {

    override suspend fun doWork(): Result {
        return coroutineScope {
            Reminder.remind(applicationContext)
            Result.success()
        }
    }

    companion object {

        private const val NAME = "ReminderWorker"

        fun enqueue(context: Context, delayInMillis: Long) {
            val request = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                request
            )
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(NAME)
        }
    }
}