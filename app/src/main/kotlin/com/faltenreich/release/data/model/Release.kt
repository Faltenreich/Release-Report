package com.faltenreich.release.data.model

import com.faltenreich.release.data.enum.ReleaseType
import com.faltenreich.release.data.preference.UserPreferences
import com.faltenreich.release.data.provider.DateProvider
import com.faltenreich.release.data.provider.TitleProvider
import com.faltenreich.release.extension.localDate
import com.faltenreich.release.parse.database.getJSONArrayValues
import com.parse.ParseObject

data class Release(
    override var id: String? = null,
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
) : Model, DateProvider, TitleProvider {

    var releaseType: ReleaseType?
        get() = type?.let { type -> ReleaseType.valueForKey(type) }
        set(value) { type = value?.key }

    var isFavorite: Boolean
        get() = id?.let { id -> UserPreferences.favoriteReleaseIds.contains(id) } ?: false
        set(value) {
            id?.let { id ->
                UserPreferences.favoriteReleaseIds = UserPreferences.favoriteReleaseIds.filter { otherId -> otherId != id }.toMutableSet().apply { if (value) add(id) }
            }
        }

    val subtitle: String?
        get() = when (releaseType) {
            ReleaseType.MOVIE -> null // TODO: Actors?
            ReleaseType.MUSIC -> artistName
            ReleaseType.GAME -> null // TODO: Platforms
            null -> null
        }

    override fun fromParseObject(parseObject: ParseObject) {
        id = parseObject.getString(Model.ID)
        type = parseObject.getString(TYPE)
        title = parseObject.getString(TITLE)
        description = parseObject.getString(DESCRIPTION)
        releaseDate = parseObject.getDate(RELEASED_AT)?.localDate
        imageUrlForThumbnail = parseObject.getString(IMAGE_URL_FOR_THUMBNAIL)
        imageUrlForCover = parseObject.getString(IMAGE_URL_FOR_COVER)
        imageUrlForWallpaper = parseObject.getString(IMAGE_URL_FOR_WALLPAPER)
        popularity = parseObject.getNumber(POPULARITY)?.toFloat()
        genres = parseObject.getJSONArrayValues(GENRES)
        platforms = parseObject.getJSONArrayValues(PLATFORMS)
    }

    companion object {
        const val TYPE = "type"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val RELEASED_AT = "releasedAt"
        const val IMAGE_URL_FOR_THUMBNAIL = "imageUrlForThumbnail"
        const val IMAGE_URL_FOR_COVER = "imageUrlForCover"
        const val IMAGE_URL_FOR_WALLPAPER = "imageUrlForWallpaper"
        const val POPULARITY = "popularity"
        const val GENRES = "genres"
        const val PLATFORMS = "platforms"

        const val FALLBACK_COVER_COLOR_RES = android.R.color.transparent
    }
}