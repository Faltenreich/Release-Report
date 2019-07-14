package com.faltenreich.release.ui.list.pagination

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.data.model.Release

class ReleaseDiffUtilCallback : DiffUtil.ItemCallback<Release>() {

    override fun areItemsTheSame(oldItem: Release, newItem: Release): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Release, newItem: Release): Boolean {
        return oldItem == newItem
    }
}