package com.faltenreich.releaseradar.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.data.model.Image
import com.faltenreich.releaseradar.ui.list.viewholder.GalleryViewHolder

class GalleryListAdapter(context: Context) : SimpleListAdapter<Image, GalleryViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GalleryViewHolder(context, parent)
}