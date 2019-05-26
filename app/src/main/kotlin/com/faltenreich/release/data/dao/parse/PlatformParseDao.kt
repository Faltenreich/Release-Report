package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.data.dao.PlatformDao
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.parse.database.ParseDao
import kotlin.reflect.KClass

class PlatformParseDao : PlatformDao, ParseDao<Platform> {
    override val clazz: KClass<Platform> = Platform::class
    override val modelName: String = "Platform"
}