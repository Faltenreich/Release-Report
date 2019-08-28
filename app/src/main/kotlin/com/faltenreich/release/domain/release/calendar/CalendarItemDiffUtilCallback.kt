package com.faltenreich.release.domain.release.calendar

import androidx.recyclerview.widget.DiffUtil

class CalendarItemDiffUtilCallback : DiffUtil.ItemCallback<CalendarItem>() {

    override fun areItemsTheSame(oldItem: CalendarItem, newItem: CalendarItem): Boolean {
        return oldItem::class == newItem::class && oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: CalendarItem, newItem: CalendarItem): Boolean {
        return oldItem == newItem
    }
}