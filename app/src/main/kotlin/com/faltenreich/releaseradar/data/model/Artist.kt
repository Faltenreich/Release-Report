package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.provider.NameProvider

data class Artist(
    override var name: String? = null
) : Entity(), NameProvider