package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.provider.NameProvider
import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName

data class Release(
    override var name: String? = null,
    @get:PropertyName("type") @set:PropertyName("type")
    var mediaTypeKey: String? = null,
    var description: String? = null,
    var durationInSeconds: Long? = null
) : Entity(), NameProvider {

    @get:Exclude @set:Exclude
    var mediaType: MediaType?
        get() = mediaTypeKey?.let { type -> MediaType.valueForKey(type) }
        set(value) { mediaTypeKey = value?.key }
}