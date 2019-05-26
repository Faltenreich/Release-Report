package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.data.dao.ImageDao
import com.faltenreich.release.data.model.Image
import com.faltenreich.release.parse.database.ParseDao
import kotlin.reflect.KClass

class ImageParseDao : ImageDao, ParseDao<Image> {
    override val clazz: KClass<Image> = Image::class
    override val modelName: String = "Image"
}