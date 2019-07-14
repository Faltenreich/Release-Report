package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.CalendarDateItem
import com.faltenreich.release.ui.list.item.CalendarItem
import com.faltenreich.release.ui.list.item.CalendarMonthItem
import com.faltenreich.release.ui.list.pagination.CalendarItemDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.CalendarDateViewHolder
import com.faltenreich.release.ui.list.viewholder.CalendarMonthViewHolder
import com.faltenreich.release.ui.list.viewholder.CalendarViewHolder

class CalendarListAdapter(context: Context) : PagedListAdapter<CalendarItem, CalendarViewHolder<CalendarItem>>(context, CalendarItemDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getListItemAt(position)) {
            is CalendarMonthItem -> VIEW_TYPE_MONTH
            is CalendarDateItem -> VIEW_TYPE_DAY
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder<CalendarItem> {
        return when (viewType) {
            VIEW_TYPE_MONTH -> CalendarMonthViewHolder(context, parent)
            VIEW_TYPE_DAY -> CalendarDateViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        } as CalendarViewHolder<CalendarItem>
    }

    companion object {
        const val VIEW_TYPE_MONTH = 0
        const val VIEW_TYPE_DAY = 1
    }
}