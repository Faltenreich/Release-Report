package com.faltenreich.release.domain.release.calendar

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import org.threeten.bp.DayOfWeek

class CalendarLayoutManager(
    context: Context,
    private val listAdapter: CalendarListAdapter?
) : GridLayoutManager(context,
    DAYS_PER_WEEK
) {

    init {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (listAdapter?.getItemViewType(position)) {
                CalendarListAdapter.VIEW_TYPE_MONTH -> DAYS_PER_WEEK
                CalendarListAdapter.VIEW_TYPE_DAY -> 1
                else -> -1
            }
        }
    }

    companion object {
        private val DAYS_PER_WEEK = DayOfWeek.values().size
    }
}