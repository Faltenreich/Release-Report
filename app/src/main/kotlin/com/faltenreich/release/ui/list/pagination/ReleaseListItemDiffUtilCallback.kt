package com.faltenreich.release.ui.list.pagination

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.ui.list.item.ReleaseListItem

class ReleaseListItemDiffUtilCallback : DiffUtil.ItemCallback<ReleaseListItem>() {

    override fun areItemsTheSame(oldItem: ReleaseListItem, newItem: ReleaseListItem): Boolean {
        return oldItem::class == newItem::class && oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: ReleaseListItem, newItem: ReleaseListItem): Boolean {
        return oldItem == newItem
    }
}