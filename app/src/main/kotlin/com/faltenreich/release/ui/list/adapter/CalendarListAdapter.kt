package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.CalendarDayListItem
import com.faltenreich.release.ui.list.item.CalendarListItem
import com.faltenreich.release.ui.list.item.CalendarMonthListItem
import com.faltenreich.release.ui.list.item.CalendarWeekDayListItem
import com.faltenreich.release.ui.list.viewholder.CalendarDayViewHolder
import com.faltenreich.release.ui.list.viewholder.CalendarMonthViewHolder
import com.faltenreich.release.ui.list.viewholder.CalendarViewHolder
import com.faltenreich.release.ui.list.viewholder.CalendarWeekDayViewHolder

class CalendarListAdapter(context: Context) : SimpleListAdapter<CalendarListItem, CalendarViewHolder<CalendarListItem>>(context) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getListItemAt(position)) {
            is CalendarMonthListItem -> VIEW_TYPE_MONTH
            is CalendarWeekDayListItem -> VIEW_TYPE_WEEK_DAY
            is CalendarDayListItem -> VIEW_TYPE_DAY
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder<CalendarListItem> {
        return when (viewType) {
            VIEW_TYPE_MONTH -> CalendarMonthViewHolder(context, parent)
            VIEW_TYPE_WEEK_DAY -> CalendarWeekDayViewHolder(context, parent)
            VIEW_TYPE_DAY -> CalendarDayViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        } as CalendarViewHolder<CalendarListItem>
    }

    companion object {
        const val VIEW_TYPE_MONTH = 0
        const val VIEW_TYPE_WEEK_DAY = 1
        const val VIEW_TYPE_DAY = 2
    }
}