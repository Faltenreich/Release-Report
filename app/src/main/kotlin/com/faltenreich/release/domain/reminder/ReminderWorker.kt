package com.faltenreich.release.domain.reminder

import android.content.Context
import androidx.work.*
import com.faltenreich.release.framework.android.architecture.workManager
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

        private const val TAG = "ReminderWorker"

        fun enqueue(context: Context, delayInMillis: Long) {
            val request = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
                .addTag(TAG)
                .build()
            context.workManager.enqueueUniquePeriodicWork(
                TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                request
            )
        }

        fun cancel(context: Context) {
            val workManager = context.workManager
            workManager.pruneWork()
            workManager.cancelAllWorkByTag(TAG)
            workManager.cancelUniqueWork(TAG)
        }
    }
}