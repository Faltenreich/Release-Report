package com.faltenreich.release.data.model

import com.parse.ParseObject

data class Image(
    override var id: String? = null,
    var url: String? = null
): Model {

    override fun fromParseObject(parseObject: ParseObject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}