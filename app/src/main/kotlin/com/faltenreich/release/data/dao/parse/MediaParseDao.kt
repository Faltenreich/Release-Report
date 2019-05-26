package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.data.dao.MediaDao
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.parse.database.ParseDao
import kotlin.reflect.KClass

class MediaParseDao : MediaDao, ParseDao<Media> {
    override val clazz: KClass<Media> = Media::class
    override val modelName: String = "Image"
}