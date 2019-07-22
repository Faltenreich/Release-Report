package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.CalendarDateItem
import com.faltenreich.release.ui.list.item.CalendarItem
import com.faltenreich.release.ui.list.item.CalendarMonthItem
import com.faltenreich.release.ui.list.pagination.CalendarItemDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.BaseViewHolder
import com.faltenreich.release.ui.list.viewholder.CalendarDayViewHolder
import com.faltenreich.release.ui.list.viewholder.CalendarMonthViewHolder

class CalendarListAdapter(context: Context) : PagedListAdapter<CalendarItem, BaseViewHolder<CalendarItem>>(context, CalendarItemDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getListItemAt(position)) {
            is CalendarMonthItem -> VIEW_TYPE_MONTH
            is CalendarDateItem -> VIEW_TYPE_DAY
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CalendarItem> {
        return when (viewType) {
            VIEW_TYPE_MONTH -> CalendarMonthViewHolder(context, parent)
            VIEW_TYPE_DAY -> CalendarDayViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        } as BaseViewHolder<CalendarItem>
    }

    companion object {
        const val VIEW_TYPE_MONTH = 0
        const val VIEW_TYPE_DAY = 1
    }
}