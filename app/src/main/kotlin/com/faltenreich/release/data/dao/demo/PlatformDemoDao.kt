package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.dao.PlatformDao
import com.faltenreich.release.data.model.Platform

class PlatformDemoDao : PlatformDao {

    private val platforms by lazy { DemoFactory.platforms }

    override suspend fun getById(id: String): Platform? {
        return platforms.firstOrNull { platform -> platform.id == id }
    }

    override suspend fun getByIds(ids: Collection<String>): List<Platform> {
        return platforms.filter { platform -> ids.contains(platform.id) }
    }
}