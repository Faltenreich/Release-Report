package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.provider.NameProvider

data class Genre(
    override var name: String? = null
) : Entity(), NameProvider