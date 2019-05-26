package com.faltenreich.release.data.model

import com.faltenreich.release.data.provider.TitleProvider
import com.parse.ParseObject

data class Genre(
    override var id: String? = null,
    override var title: String? = null
) : Model, TitleProvider {

    override fun fromParseObject(parseObject: ParseObject) {
        id = parseObject.getString(Model.ID)
        title = parseObject.getString(TITLE)
    }

    companion object {
        const val TITLE = "title"
    }
}