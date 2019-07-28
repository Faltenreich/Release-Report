package com.faltenreich.release.ui.viewpager

import com.faltenreich.release.R
import com.faltenreich.release.data.enum.MediaType
import com.faltenreich.release.data.model.Media

class GalleryPagerAdapter : ViewPagerAdapter<Media>(R.layout.list_item_gallery) {
    override fun bind(data: Media) {
        data.url?.let { url ->
            when (data.mediaType) {
                MediaType.IMAGE -> Unit // TODO
                MediaType.VIDEO -> Unit // TODO
                else -> throw IllegalArgumentException("Unsupported media type: ${data.mediaType}")
            }
        }
    }
}