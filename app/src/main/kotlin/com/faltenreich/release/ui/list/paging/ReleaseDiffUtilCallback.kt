package com.faltenreich.release.ui.list.paging

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.data.model.Release

class ReleaseDiffUtilCallback : DiffUtil.ItemCallback<Release>() {
    override fun areItemsTheSame(oldItem: Release, newItem: Release): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Release, newItem: Release): Boolean = oldItem == newItem
}