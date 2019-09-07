package com.faltenreich.release

import android.app.Application
import com.faltenreich.release.domain.preference.FavoriteManager
import com.faltenreich.release.domain.preference.UserPreferences
import com.faltenreich.release.domain.reminder.Reminder
import com.faltenreich.release.framework.parse.ParseServer
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
        FavoriteManager.init()
        Reminder.refresh(this)
    }

    companion object {
        const val isDemo: Boolean = BuildConfig.FLAVOR == "demo"
        const val isBeta: Boolean = BuildConfig.FLAVOR == "beta"
    }
}