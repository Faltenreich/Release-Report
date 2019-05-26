package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.data.model.Image
import com.faltenreich.release.ui.list.viewholder.GalleryViewHolder

class GalleryListAdapter(context: Context) : SimpleListAdapter<Image, GalleryViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GalleryViewHolder(context, parent)
}