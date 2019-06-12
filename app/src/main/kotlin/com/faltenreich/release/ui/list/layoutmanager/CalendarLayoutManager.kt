package com.faltenreich.release.ui.list.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.ui.list.adapter.CalendarListAdapter

class CalendarLayoutManager(
    context: Context,
    private val listAdapter: CalendarListAdapter?
) : GridLayoutManager(context, SPAN_COUNT) {

    init {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (listAdapter?.getItemViewType(position)) {
                CalendarListAdapter.VIEW_TYPE_MONTH -> SPAN_COUNT
                else -> 1
            }
        }
    }

    companion object {
        private const val SPAN_COUNT = 7
    }
}