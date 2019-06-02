package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.CalendarListItem
import com.faltenreich.release.ui.list.viewholder.CalendarViewHolder

class CalendarListAdapter(context: Context) : SimpleListAdapter<CalendarListItem, CalendarViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CalendarViewHolder(context, parent)
}