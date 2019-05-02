package com.faltenreich.releaseradar.data.model

abstract class Entity(
    override var id: String? = null,
    override var updatedAt: String? = null
) : Storable