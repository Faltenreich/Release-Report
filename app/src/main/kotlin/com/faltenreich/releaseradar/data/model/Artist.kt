package com.faltenreich.releaseradar.data.model

import com.faltenreich.releaseradar.data.provider.TitleProvider
import com.parse.ParseObject

data class Artist(
    override var title: String? = null,
    var imageUrl: String? = null,
    var imageThumbnailUrl: String? = null
) : BaseEntity(), TitleProvider {

    override fun fromParseObject(parseObject: ParseObject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}