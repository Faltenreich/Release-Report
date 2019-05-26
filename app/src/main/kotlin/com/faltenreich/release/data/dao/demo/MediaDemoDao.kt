package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.dao.MediaDao
import com.faltenreich.release.data.model.Media

class MediaDemoDao : MediaDao {
    private val images by lazy { DemoFactory.media() }

    override fun getById(id: String, onResult: (Media?) -> Unit) {
        onResult(images.firstOrNull { image -> image.id == id })
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<Media>) -> Unit) {
        onResult(images.filter { image -> ids.contains(image.id) })
    }
}