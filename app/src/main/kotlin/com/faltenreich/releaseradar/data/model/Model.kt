package com.faltenreich.releaseradar.data.model

import com.parse.ParseObject

interface Model {
    var id: String?

    fun fromParseObject(parseObject: ParseObject)

    companion object {
        const val ID = "externalId"
    }
}F