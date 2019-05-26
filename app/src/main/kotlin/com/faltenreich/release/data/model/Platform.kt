package com.faltenreich.release.data.model

import com.faltenreich.release.data.provider.DateProvider
import com.faltenreich.release.data.provider.TitleProvider
import com.parse.ParseObject

data class Platform(
    override var id: String? = null,
    override var releasedAt: String? = null,
    override var title: String? = null,
    var imageUrlForThumbnail: String? = null,
    var imageUrlForCover: String? = null
) : Model, DateProvider, TitleProvider {

    override fun fromParseObject(parseObject: ParseObject) {
        id = parseObject.getString(Model.ID)
        title = parseObject.getString(TITLE)
    }

    companion object {
        const val TITLE = "title"
    }
}