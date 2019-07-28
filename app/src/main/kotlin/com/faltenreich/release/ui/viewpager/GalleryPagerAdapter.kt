package com.faltenreich.release.ui.viewpager

import android.view.View
import com.faltenreich.release.R
import com.faltenreich.release.data.enum.MediaType
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.extension.setImageAsync
import com.github.chrisbanes.photoview.PhotoView

class GalleryPagerAdapter : ViewPagerAdapter<Media>(R.layout.list_item_gallery) {
    override fun bind(data: Media, view: View) {
        // TODO: Use view binding (through ViewPager2?)
        val imageView = view.findViewById<PhotoView>(R.id.imageView)
        data.url?.let { url ->
            when (data.mediaType) {
                MediaType.IMAGE -> imageView.setImageAsync(url)
                MediaType.VIDEO -> Unit // TODO
                else -> throw IllegalArgumentException("Unsupported media type: ${data.mediaType}")
            }
        }
    }
}