package com.faltenreich.release.domain.reminder

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class ReminderWorker(
    applicationContext: Context,
    params: WorkerParameters
) : Worker(applicationContext, params) {

    override fun doWork(): Result {
        ReminderManager.remind(applicationContext)
        return Result.success()
    }

    companion object {

        private const val NAME = "ReminderWorker"

        fun enqueue(context: Context) {
            val request = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                request
            )
        }
    }
}