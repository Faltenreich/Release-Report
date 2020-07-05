package com.faltenreich.release.domain.release.calendar

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.base.date.isToday
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.domain.date.DateOpener
import com.faltenreich.release.framework.android.context.getColorFromAttribute
import com.faltenreich.release.framework.android.view.recyclerview.viewholder.BaseViewHolder
import com.faltenreich.release.framework.glide.setImageAsync
import kotlinx.android.synthetic.main.list_item_calendar_day.*

class CalendarDayViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<CalendarDayItem>(
    context,
    R.layout.list_item_calendar_day,
    parent
), DateOpener {

    init {
        container.setOnClickListener { openDate(navigationController, data.date) }
    }

    override fun onBind(data: CalendarDayItem) {
        val (date, _, calendarEvent) = data
        val isToday = date.isToday
        val isInSameMonth = data.isInSameMonth

        dayLabel.text = date.dayOfMonth.toString()
        dayLabel.setTextColor(
            if (isInSameMonth)
                when {
                    isToday -> context.getColorFromAttribute(android.R.attr.textColorPrimaryInverse)
                    calendarEvent?.imageUrl != null -> ContextCompat.getColor(context, android.R.color.white)
                    else -> context.getColorFromAttribute(android.R.attr.textColorPrimary)
                }
            else context.getColorFromAttribute(android.R.attr.textColorSecondary)
        )
        todayIndicator.isVisible = isInSameMonth && isToday

        coverScrim.isVisible = false
        coverView.setImageResource(android.R.color.transparent)
        calendarEvent?.imageUrl?.let { url ->
            coverView.setImageAsync(url) { drawable ->
                if (drawable != null) {
                    dayLabel.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    coverScrim.isVisible = true
                }
            }
        }
        subscriptionImageView.isVisible = calendarEvent?.isSubscribed.isTrue
    }
}