package com.faltenreich.release.ui.list.pagination

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.ui.list.item.CalendarItem

class CalendarListItemDiffUtilCallback : DiffUtil.ItemCallback<CalendarItem>() {

    override fun areItemsTheSame(oldItem: CalendarItem, newItem: CalendarItem): Boolean {
        return oldItem::class == newItem::class && oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: CalendarItem, newItem: CalendarItem): Boolean {
        return oldItem == newItem
    }
}