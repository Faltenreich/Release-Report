package com.faltenreich.release.ui.list.pagination

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.ui.list.item.ListItem

class ListDiffUtilCallback : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem::class == newItem::class && oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}