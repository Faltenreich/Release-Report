package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.provider.DateProvider
import com.faltenreich.releaseradar.data.provider.TitleProvider
import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName

data class Release(
    override var title: String? = null,
    @get:PropertyName("type") @set:PropertyName("type")
    var mediaTypeKey: String? = null,
    var artistName: String? = null,
    var description: String? = null,
    var durationInSeconds: Long? = null,
    var imageUrlForThumbnail: String? = null,
    var imageUrlForCover: String? = null,
    var imageUrlForWallpaper: String? = null,
    var videoUrl: String? = null,
    @get:PropertyName("releasedAt") @set:PropertyName("releasedAt")
    override var releasedAtString: String? = null,
    var popularity: Float? = null,
    var rating: Float? = null,
    var rateCount: Int? = null,
    var externalUrl: String? = null
) : Entity(), TitleProvider, DateProvider {

    @get:Exclude @set:Exclude
    var mediaType: MediaType?
        get() = mediaTypeKey?.let { type -> MediaType.valueForKey(type) }
        set(value) { mediaTypeKey = value?.key }
}