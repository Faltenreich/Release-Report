package com.faltenreich.release.ui.list.pagination

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.ui.list.item.DateItem

class ReleaseListItemDiffUtilCallback : DiffUtil.ItemCallback<DateItem>() {

    override fun areItemsTheSame(oldItem: DateItem, newItem: DateItem): Boolean {
        return oldItem::class == newItem::class && oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: DateItem, newItem: DateItem): Boolean {
        return oldItem == newItem
    }
}