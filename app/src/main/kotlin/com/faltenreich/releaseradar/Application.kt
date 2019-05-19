package com.faltenreich.releaseradar

import android.app.Application
import com.faltenreich.releaseradar.data.preference.UserPreferences
import com.faltenreich.releaseradar.parse.ParseServer
import com.jakewharton.threetenabp.AndroidThreeTen

@Suppress("unused")
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        AndroidThreeTen.init(this)
        UserPreferences.init(this)
        ParseServer.init(this)
        // ReminderManager.remind(this)
    }

    companion object {
        val isDemo: Boolean
            get() = BuildConfig.FLAVOR == "demo"
        val isBeta: Boolean
            get() = BuildConfig.FLAVOR == "beta"
    }
}