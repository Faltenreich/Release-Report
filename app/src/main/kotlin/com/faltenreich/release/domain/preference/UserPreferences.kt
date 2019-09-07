package com.faltenreich.release.domain.preference

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.faltenreich.release.base.date.asLocalTime
import com.faltenreich.release.base.date.asString
import org.threeten.bp.LocalTime

object UserPreferences : SharedPreferences() {

    private const val KEY_FAVORITE_RELEASE_IDS = "favoriteReleaseIds"
    private const val KEY_REMINDER_TIME = "reminderTime"

    private val REMINDER_TIME_DEFAULT = LocalTime.of(9, 0)

    var favoriteReleaseIds: Set<String>
        get() = getStringSet(KEY_FAVORITE_RELEASE_IDS)
        set(value) = setStringSet(KEY_FAVORITE_RELEASE_IDS to value)

    var reminderTime: LocalTime
        get() = get<String>(KEY_REMINDER_TIME).takeIf(String::isNotBlank)?.asLocalTime ?: REMINDER_TIME_DEFAULT
        set(value) = set(KEY_REMINDER_TIME to value.asString)

    fun registerOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}