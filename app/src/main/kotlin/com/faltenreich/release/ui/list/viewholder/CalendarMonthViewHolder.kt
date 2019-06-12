package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.extension.printMonth
import com.faltenreich.release.ui.list.item.CalendarMonthListItem
import kotlinx.android.synthetic.main.list_item_calendar_weekday.*

class CalendarMonthViewHolder(context: Context, parent: ViewGroup) : CalendarViewHolder<CalendarMonthListItem>(context, R.layout.list_item_calendar_month, parent) {

    override fun onBind(data: CalendarMonthListItem) {
        label.text = data.date.printMonth(context)
    }
}