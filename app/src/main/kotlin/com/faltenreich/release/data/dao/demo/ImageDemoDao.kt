package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.dao.ImageDao
import com.faltenreich.release.data.model.Image

class ImageDemoDao : ImageDao {
    private val images by lazy { DemoFactory.images() }

    override fun getById(id: String, onResult: (Image?) -> Unit) {
        onResult(images.firstOrNull { image -> image.id == id })
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<Image>) -> Unit) {
        onResult(images.filter { image -> ids.contains(image.id) })
    }
}