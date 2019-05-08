package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.provider.TitleProvider
import com.parse.ParseObject

data class Genre(
    override var title: String? = null
) : BaseEntity(), TitleProvider {

    override fun fromParseObject(parseObject: ParseObject) {
        id = parseObject.getString(ID)
        title = parseObject.getString(TITLE)
    }

    companion object {
        const val TITLE = "title"
    }
}