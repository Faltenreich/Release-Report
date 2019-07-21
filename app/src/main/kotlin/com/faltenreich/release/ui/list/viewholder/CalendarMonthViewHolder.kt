package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.extension.print
import com.faltenreich.release.ui.list.item.CalendarMonthItem
import kotlinx.android.synthetic.main.list_item_calendar_month.*

class CalendarMonthViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<CalendarMonthItem>(context, R.layout.list_item_calendar_month, parent) {
    override fun onBind(data: CalendarMonthItem) {
        monthLabel.text = data.yearMonth.print()
    }
}