package com.faltenreich.release.domain.reminder

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderWorker(
    applicationContext: Context,
    params: WorkerParameters
) : Worker(applicationContext, params) {

    override fun doWork(): Result {
        ReminderManager.remind(applicationContext)
        return Result.success()
    }
}