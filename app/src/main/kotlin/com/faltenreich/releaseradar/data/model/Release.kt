package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.provider.DateProvider
import com.faltenreich.releaseradar.data.provider.NameProvider
import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName
import org.threeten.bp.LocalDate

data class Release(
    override var name: String? = null,
    @get:PropertyName("type") @set:PropertyName("type")
    var mediaTypeKey: String? = null,
    var description: String? = null,
    var durationInSeconds: Long? = null,
    var imageUrl: String? = null,
    var artistName: String? = null,
    @get:PropertyName("releasedAt") @set:PropertyName("releasedAt")
    override var releasedAtString: String? = null,
    var popularity: Float? = null,
    var rating: Float? = null,
    var rateCount: Int? = null
) : Entity(), NameProvider, DateProvider {

    @get:Exclude @set:Exclude
    var mediaType: MediaType?
        get() = mediaTypeKey?.let { type -> MediaType.valueForKey(type) }
        set(value) { mediaTypeKey = value?.key }
}