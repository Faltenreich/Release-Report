package com.faltenreich.release.framework.android.architecture

import android.content.Context
import androidx.work.WorkManager

val Context.workManager: WorkManager
    get() = WorkManager.getInstance(this)