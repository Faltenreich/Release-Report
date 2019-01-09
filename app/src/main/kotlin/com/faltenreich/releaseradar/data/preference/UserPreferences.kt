package com.faltenreich.releaseradar.data.preference

object UserPreferences : SharedPreferences() {

    private const val KEY_FAVORITE_RELEASE_IDS = "favoriteReleaseIds"

    var favoriteReleaseIds: Set<String>
        get() = getStringSet(KEY_FAVORITE_RELEASE_IDS)
        set(value) = setStringSet(KEY_FAVORITE_RELEASE_IDS to value)
}