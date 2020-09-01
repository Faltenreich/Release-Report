package com.faltenreich.release.framework.scrollgalleryview

import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import com.veinhorn.scrollgalleryview.ScrollGalleryView

var ScrollGalleryView.currentItemWithThumbnail: Int
    get() = currentItem
    set(value) {
        currentItem = value
        // Workaround: ScrollGalleryView.setCurrentItem() does not scroll to Thumbnail, so we simulate a click on it
        val thumbnailsContainer = findViewById<ViewGroup>(com.veinhorn.scrollgalleryview.R.id.thumbnails_container)
        thumbnailsContainer.getChildAt(value)?.doOnPreDraw { thumbnail -> thumbnail.performClick() }
    }