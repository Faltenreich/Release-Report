package com.faltenreich.releaseradar.data.dao.parse

import com.faltenreich.releaseradar.data.dao.ImageDao
import com.faltenreich.releaseradar.data.model.Image
import com.faltenreich.releaseradar.parse.database.ParseDao
import kotlin.reflect.KClass

class ImageParseDao : ImageDao, ParseDao<Image> {
    override val clazz: KClass<Image> = Image::class
    override val modelName: String = "Image"
}