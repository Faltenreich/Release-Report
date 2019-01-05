package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.provider.DateProvider
import com.faltenreich.releaseradar.data.provider.TitleProvider

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
    var imageUrlForWallpaper: String? = null
) : Entity(), TitleProvider, DateProvider {

    var mediaType: MediaType?
        get() = type?.let { type -> MediaType.valueForKey(type) }
        set(value) { type = value?.key }
}