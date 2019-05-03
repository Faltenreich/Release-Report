package com.faltenreich.releaseradar.data.model

import com.parse.ParseObject

interface Entity {
    var id: String?

    fun fromParseObject(parseObject: ParseObject) {
        TODO()
    }
    fun toParseObject(): ParseObject? {
        TODO()
    }
}