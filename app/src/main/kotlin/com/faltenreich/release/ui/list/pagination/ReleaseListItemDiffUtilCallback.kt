package com.faltenreich.release.ui.list.pagination

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.ui.list.item.ReleaseListItem

class ReleaseListItemDiffUtilCallback : DiffUtil.ItemCallback<ReleaseListItem>() {
    override fun areItemsTheSame(oldItem: ReleaseListItem, newItem: ReleaseListItem): Boolean = oldItem.date == newItem.date && oldItem.release == newItem.release
    override fun areContentsTheSame(oldItem: ReleaseListItem, newItem: ReleaseListItem): Boolean = oldItem == newItem
}