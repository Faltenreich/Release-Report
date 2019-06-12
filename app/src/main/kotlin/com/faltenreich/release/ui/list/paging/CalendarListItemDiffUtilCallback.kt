package com.faltenreich.release.ui.list.paging

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.ui.list.item.CalendarListItem

class CalendarListItemDiffUtilCallback : DiffUtil.ItemCallback<CalendarListItem>() {

    override fun areItemsTheSame(oldItem: CalendarListItem, newItem: CalendarListItem): Boolean {
        return oldItem::class == newItem::class && oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: CalendarListItem, newItem: CalendarListItem): Boolean {
        return oldItem == newItem
    }
}