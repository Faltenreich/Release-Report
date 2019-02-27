package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.preference.UserPreferences
import com.faltenreich.releaseradar.data.provider.DateProvider
import com.faltenreich.releaseradar.data.provider.TitleProvider
import com.faltenreich.releaseradar.extension.isTrue

data class Release(
    override var releasedAt: String? = null,
    override var title: String? = null,
    var type: String? = null,
    var artistName: String? = null,
    var description: String? = null,
    var durationInSeconds: Long? = null,
    var popularity: Float? = null,
    var externalUrl: String? = null,
    var videoUrl: String? = null,
    var imageUrlForThumbnail: String? = null,
    var imageUrlForCover: String? = null,
    var imageUrlForWallpaper: String? = null,
    var indexForSpotlight: String? = null,
    var genres: List<String>? = null,
    var platforms: List<String>? = null
) : Entity(), DateProvider, TitleProvider {

    var mediaType: MediaType?
        get() = type?.let { type -> MediaType.valueForKey(type) }
        set(value) { type = value?.key }

    var isFavorite: Boolean
        get() = id?.let { id -> UserPreferences.favoriteReleaseIds.contains(id) } ?: false
        set(value) {
            id?.let { id ->
                UserPreferences.favoriteReleaseIds = UserPreferences.favoriteReleaseIds.filter { otherId -> otherId != id }.toMutableSet().apply { if (value) add(id) }
            }
        }

    // TODO: Split query by whitespaces and support multi-keyword matching
    fun matches(query: String) = title?.contains(query, true).isTrue || artistName?.contains(query, true).isTrue
}