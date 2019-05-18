package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Platform

object PlatformDao : BaseDao<Platform>(Platform::class) {
    override val modelName: String = "Platform"
}