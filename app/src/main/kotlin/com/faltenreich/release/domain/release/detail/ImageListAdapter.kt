package com.faltenreich.release.domain.release.detail

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.framework.android.adapter.MutableListAdapter

class ImageListAdapter(
    context: Context
) : MutableListAdapter<String, ImageViewHolder>(
    context
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(context, parent)
    }
}