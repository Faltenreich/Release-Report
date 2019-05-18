package com.faltenreich.releaseradar.data.dao.parse

import com.faltenreich.releaseradar.data.dao.PlatformDao
import com.faltenreich.releaseradar.data.model.Platform
import com.faltenreich.releaseradar.parse.database.ParseDao
import kotlin.reflect.KClass

class PlatformParseDao : PlatformDao, ParseDao<Platform> {
    override val clazz: KClass<Platform> = Platform::class
    override val modelName: String = "Platform"
}