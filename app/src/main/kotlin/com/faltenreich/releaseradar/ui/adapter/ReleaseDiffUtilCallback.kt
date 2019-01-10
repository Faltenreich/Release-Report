package com.faltenreich.releaseradar.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.releaseradar.data.model.Release

class ReleaseDiffUtilCallback : DiffUtil.ItemCallback<Release>() {
    override fun areItemsTheSame(oldItem: Release, newItem: Release): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Release, newItem: Release): Boolean = oldItem == newItem
}