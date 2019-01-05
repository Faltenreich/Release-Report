package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.provider.TitleProvider

data class Platform(
    override var title: String? = null
) : Entity(), TitleProvider