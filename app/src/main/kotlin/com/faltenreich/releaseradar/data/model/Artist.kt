package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.provider.TitleProvider

data class Artist(
    override var title: String? = null,
    var imageUrl: String? = null,
    var imageThumbnailUrl: String? = null
) : Entity(), TitleProvider