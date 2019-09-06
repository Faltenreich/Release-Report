package com.faltenreich.release.domain.preference

import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository

// Caches favorite releases whose ids are stored via shared preferences
object FavoriteManager {

    private var favorites: MutableSet<Release> = mutableSetOf()

    fun init() {
        val favoriteReleaseIds = UserPreferences.favoriteReleaseIds
        // TODO: Clean favorites at some point to prevent exploding data set
        ReleaseRepository.getByIds(favoriteReleaseIds) { releases ->
            favorites.addAll(releases)
        }
    }

    private fun invalidate() {
        UserPreferences.favoriteReleaseIds = favorites.mapNotNull(Release::id).toSet()
    }

    // FIXME: May be empty due to init() not being processed yet - make asynchronous
    fun getFavorites(): List<Release> {
        return favorites.toList()
    }

    fun isFavorite(release: Release): Boolean {
        return favorites.contains(release).isTrue
    }

    fun addFavorite(release: Release) {
        favorites.add(release)
        invalidate()
    }

    fun removeFavorite(release: Release) {
        favorites.remove(release)
        invalidate()
    }
}