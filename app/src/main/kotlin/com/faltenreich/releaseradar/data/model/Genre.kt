package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.provider.TitleProvider

data class Genre(
    override var title: String? = null
) : BaseEntity(), TitleProvider