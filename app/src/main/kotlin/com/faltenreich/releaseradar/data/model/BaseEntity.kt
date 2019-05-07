package com.faltenreich.releaseradar.data.model

abstract class BaseEntity(
    override var id: String? = null
) : Entity {

    companion object {
        const val ID = "externalId"
    }
}