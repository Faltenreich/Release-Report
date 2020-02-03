package com.faltenreich.release.data.model

import com.faltenreich.release.base.date.localDate
import com.faltenreich.release.data.enum.ReleaseType
import com.faltenreich.release.data.provider.ReleaseDateProvider
import com.faltenreich.release.data.provider.TitleProvider
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.framework.parse.database.getJSONArrayValues
import com.parse.ParseObject

data class Release(
    override var id: String? = null,
    override var releasedAt: String? = null,
    override var title: String? = null,
    var type: String? = null,
    var artist: String? = null,
    var description: String? = null,
    var durationInSeconds: Long? = null,
    var popularity: Float? = null,
    var externalUrl: String? = null,
    var imageUrlForThumbnail: String? = null,
    var imageUrlForCover: String? = null,
    var imageUrlForWallpaper: String? = null,
    var imageUrls: List<String>? = null,
    var videoUrls: List<String>? = null,
    var genres: List<String>? = null,
    var platforms: List<String>? = null
) : Model, ReleaseDateProvider, TitleProvider {

    var releaseType: ReleaseType?
        get() = type?.let { type -> ReleaseType.valueForKey(type) }
        set(value) { type = value?.key }

    val artistIfRelevant: String?
        get() = when (releaseType) {
            ReleaseType.MUSIC -> artist
            else -> null
        }

    val titleFull: String?
        get() = artistIfRelevant?.let { artist -> "$artist - $title" } ?: title

    var isSubscribed: Boolean
        get() = ReleaseRepository.isSubscribed(this)
        set(value) {
            if (value) {
                ReleaseRepository.subscribe(this)
            } else {
                ReleaseRepository.unsubscribe(this)
            }
        }

    val imageUrlsFull: List<String>
        get() = listOfNotNull(
            imageUrlForCover,
            imageUrlForWallpaper
        ).run {
            imageUrls?.let { imageUrls -> plus(imageUrls) } ?: this
        }

    override fun fromParseObject(parseObject: ParseObject) {
        id = parseObject.getString(Model.ID)
        type = parseObject.getString(TYPE)
        artist = parseObject.getString(ARTIST)
        title = parseObject.getString(TITLE)
        description = parseObject.getString(DESCRIPTION)
        releaseDate = parseObject.getDate(RELEASED_AT)?.localDate
        popularity = parseObject.getNumber(POPULARITY)?.toFloat()
        externalUrl = parseObject.getString(EXTERNAL_URL)
        imageUrlForThumbnail = parseObject.getString(IMAGE_URL_FOR_THUMBNAIL)
        imageUrlForCover = parseObject.getString(IMAGE_URL_FOR_COVER)
        imageUrlForWallpaper = parseObject.getString(IMAGE_URL_FOR_WALLPAPER)
        imageUrls = parseObject.getJSONArrayValues(IMAGE_URLS)
        videoUrls = parseObject.getJSONArrayValues(VIDEO_URLS)
        genres = parseObject.getJSONArrayValues(GENRES)
        platforms = parseObject.getJSONArrayValues(PLATFORMS)
    }

    companion object {
        const val TYPE = "type"
        const val ARTIST = "artist"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val RELEASED_AT = "releasedAt"
        const val POPULARITY = "popularity"
        const val EXTERNAL_URL = "externalUrl"
        const val IMAGE_URL_FOR_THUMBNAIL = "imageUrlForThumbnail"
        const val IMAGE_URL_FOR_COVER = "imageUrlForCover"
        const val IMAGE_URL_FOR_WALLPAPER = "imageUrlForWallpaper"
        const val IMAGE_URLS = "imageUrls"
        const val VIDEO_URLS = "videoUrls"
        const val GENRES = "genres"
        const val PLATFORMS = "platforms"
    }
}