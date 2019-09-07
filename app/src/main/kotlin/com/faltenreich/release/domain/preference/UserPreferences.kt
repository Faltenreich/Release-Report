package com.faltenreich.release.domain.preference

import com.faltenreich.release.base.date.asLocalTime
import com.faltenreich.release.base.date.asString
import org.threeten.bp.LocalTime

object UserPreferences : SharedPreferences() {

    // TODO: Reuse ids from preferences.xml
    private const val KEY_SUBSCRIBED_RELEASE_IDS = "subscribed_release_ids"
    private const val KEY_REMINDER_IS_ENABLED = "reminder_is_enabled"
    private const val KEY_REMINDER_TIME = "reminder_time"

    private val REMINDER_TIME_DEFAULT = LocalTime.of(9, 0)

    var subscribedReleaseIds: Set<String>
        get() = getStringSet(KEY_SUBSCRIBED_RELEASE_IDS)
        set(value) = setStringSet(KEY_SUBSCRIBED_RELEASE_IDS to value)

    var reminderIsEnabled: Boolean
        get() = get(KEY_REMINDER_IS_ENABLED, true)
        set(value) = set(KEY_REMINDER_IS_ENABLED to value)

    var reminderTime: LocalTime
        get() = get<String>(KEY_REMINDER_TIME).takeIf(String::isNotBlank)?.asLocalTime ?: REMINDER_TIME_DEFAULT
        set(value) = set(KEY_REMINDER_TIME to value.asString)
}