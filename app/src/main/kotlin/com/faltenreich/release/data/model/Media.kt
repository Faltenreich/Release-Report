package com.faltenreich.release.data.model

import com.faltenreich.release.data.enum.MediaType
import com.parse.ParseObject

data class Media(
    override var id: String? = null,
    var type: String? = null,
    var url: String? = null
) : Model {

    var mediaType: MediaType?
        get() = type?.let { type -> MediaType.valueForKey(type) }
        set(value) { type = value?.key }

    override fun fromParseObject(parseObject: ParseObject) {
        id = parseObject.getString(Model.ID)
        type = parseObject.getString(TYPE)
        url = parseObject.getString(URL)
    }

    companion object {
        const val TYPE = "type"
        const val URL = "URL"
    }
}