package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.extension.locale
import com.faltenreich.release.ui.list.item.CalendarWeekDayListItem
import kotlinx.android.synthetic.main.list_item_calendar_weekday.*
import org.threeten.bp.format.TextStyle

class CalendarWeekDayViewHolder(context: Context, parent: ViewGroup) : CalendarViewHolder<CalendarWeekDayListItem>(context, R.layout.list_item_calendar_weekday, parent) {

    override fun onBind(data: CalendarWeekDayListItem) {
        label.text = data.date.dayOfWeek.getDisplayName(TextStyle.SHORT, context.locale).toUpperCase()
    }
}