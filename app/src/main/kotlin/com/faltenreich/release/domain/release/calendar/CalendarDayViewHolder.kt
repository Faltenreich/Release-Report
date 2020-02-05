package com.faltenreich.release.domain.release.calendar

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.base.date.isToday
import com.faltenreich.release.domain.date.DateOpener
import com.faltenreich.release.framework.android.view.setImageAsync
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder
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
        val (date, _, releases) = data
        val isToday = date.isToday
        val isInSameMonth = data.isInSameMonth

        dayLabel.text = date.dayOfMonth.toString()
        dayLabel.setTextColor(
            ContextCompat.getColor(
                context,
                if (isInSameMonth) android.R.color.white
                else R.color.gray
            )
        )
        dayLabel.setTypeface(
            dayLabel.typeface,
            if (isToday) Typeface.BOLD else Typeface.NORMAL
        )

        val subscription = releases?.firstOrNull { release -> release.isSubscribed }
        subscription?.imageUrlForCover?.let { url ->
            coverView.isVisible = true
            coverView.setImageAsync(url, itemView.width)
        } ?: run {
            coverView.isGone = true
        }
    }
}