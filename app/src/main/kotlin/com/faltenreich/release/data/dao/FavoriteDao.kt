package com.faltenreich.release.data.dao

import com.faltenreich.release.data.preference.UserPreferences

object FavoriteDao {
    private val favoriteIds: MutableSet<String> = UserPreferences.favoriteReleaseIds.toMutableSet()

    private fun invalidate() {
        UserPreferences.favoriteReleaseIds = favoriteIds
    }

    fun getFavorites(): MutableSet<String> {
        return favoriteIds
    }

    fun isFavorite(id: String): Boolean {
        return favoriteIds.contains(id)
    }

    fun addFavorite(id: String) {
        favoriteIds.add(id)
        invalidate()
    }

    fun removeFavorite(id: String) {
        favoriteIds.remove(id)
        invalidate()
    }
}