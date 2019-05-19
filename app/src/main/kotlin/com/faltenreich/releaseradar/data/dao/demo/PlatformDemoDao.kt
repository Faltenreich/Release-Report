package com.faltenreich.releaseradar.data.dao.demo

import com.faltenreich.releaseradar.data.dao.PlatformDao
import com.faltenreich.releaseradar.data.model.Platform

class PlatformDemoDao : PlatformDao {
    private val platforms by lazy { DemoFactory.platforms() }

    override fun getById(id: String, onResult: (Platform?) -> Unit) {
        onResult(platforms.firstOrNull { platform -> platform.id == id })
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<Platform>) -> Unit) {
        onResult(platforms.filter { platform -> ids.contains(platform.id) })
    }
}