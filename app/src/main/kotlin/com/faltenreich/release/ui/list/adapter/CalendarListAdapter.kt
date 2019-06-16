package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.CalendarDayListItem
import com.faltenreich.release.ui.list.item.CalendarListItem
import com.faltenreich.release.ui.list.item.CalendarMonthListItem
import com.faltenreich.release.ui.list.paging.CalendarListItemDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.CalendarDayViewHolder
import com.faltenreich.release.ui.list.viewholder.CalendarMonthViewHolder
import com.faltenreich.release.ui.list.viewholder.CalendarViewHolder

class CalendarListAdapter(context: Context) : PagedListAdapter<CalendarListItem, CalendarViewHolder<CalendarListItem>>(context, CalendarListItemDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getListItemAt(position)) {
            is CalendarMonthListItem -> VIEW_TYPE_MONTH
            is CalendarDayListItem -> VIEW_TYPE_DAY
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder<CalendarListItem> {
        return when (viewType) {
            VIEW_TYPE_MONTH -> CalendarMonthViewHolder(context, parent)
            VIEW_TYPE_DAY -> CalendarDayViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        } as CalendarViewHolder<CalendarListItem>
    }

    companion object {
        const val VIEW_TYPE_MONTH = 0
        const val VIEW_TYPE_DAY = 1
    }
}