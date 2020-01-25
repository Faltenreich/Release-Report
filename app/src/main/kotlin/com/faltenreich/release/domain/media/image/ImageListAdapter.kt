package com.faltenreich.release.domain.media.image

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.framework.android.adapter.MutableListAdapter

class ImageListAdapter(
    context: Context,
    private val onImageSelected: (String) -> Unit
) : MutableListAdapter<String, ImageViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            context,
            parent,
            onImageSelected
        )
    }
}