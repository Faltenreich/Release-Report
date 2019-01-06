package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.provider.DateProvider
import com.faltenreich.releaseradar.data.provider.TitleProvider

data class Platform(
    override var releasedAt: String? = null,
    override var title: String? = null,
    var imageUrlForThumbnail: String? = null,
    var imageUrlForCover: String? = null
) : Entity(), DateProvider, TitleProvider