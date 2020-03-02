package com.faltenreich.release.domain.release.calendar

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.base.date.print
import com.faltenreich.release.framework.android.recyclerview.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_calendar_month.*

class CalendarMonthViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<CalendarMonthItem>(context, R.layout.list_item_calendar_month, parent) {
    override fun onBind(data: CalendarMonthItem) {
        monthLabel.text = data.yearMonth.print()
    }
}