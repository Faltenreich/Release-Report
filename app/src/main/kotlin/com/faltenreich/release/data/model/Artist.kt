package com.faltenreich.release.data.model

import com.faltenreich.release.data.provider.TitleProvider
import com.parse.ParseObject

data class Artist(
    override var id: String? = null,
    override var title: String? = null,
    var imageUrl: String? = null,
    var imageThumbnailUrl: String? = null
) : Model, TitleProvider {

    override fun fromParseObject(parseObject: ParseObject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}