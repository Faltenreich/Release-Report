package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.extension.printMonth
import com.faltenreich.release.ui.list.item.CalendarMonthListItem
import kotlinx.android.synthetic.main.list_item_calendar_weekday.*
import org.threeten.bp.LocalDate

class CalendarMonthViewHolder(context: Context, parent: ViewGroup) : CalendarViewHolder<CalendarMonthListItem>(context, R.layout.list_item_calendar_month, parent) {

    override fun onBind(data: CalendarMonthListItem) {
        val date = data.date
        val year = date.year
        val isSameYear = year == LocalDate.now().year
        val monthText = data.date.printMonth(context)
        label.text = if (isSameYear) monthText else "$monthText $year"
    }
}