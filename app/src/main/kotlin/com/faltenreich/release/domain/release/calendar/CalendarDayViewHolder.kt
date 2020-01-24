package com.faltenreich.release.domain.release.calendar

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.base.date.isToday
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.domain.date.DateOpener
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

    override fun onBind(data: CalendarDayItem) {
        val (date, _, releases) = data
        val isToday = date.isToday
        val isInSameMonth = data.isInSameMonth
        val containsSubscription = releases?.any { release -> release.isSubscribed }.isTrue

        clickable.setOnClickListener { openDate(navigationController, date) }

        dayLabel.text = date.dayOfMonth.toString()
        dayLabel.setTextColor(ContextCompat.getColor(context,
            if (isInSameMonth) android.R.color.white
            else R.color.gray
        ))

        container.setBackgroundResource(
            if (isInSameMonth && isToday) R.color.colorPrimaryDarker
            else android.R.color.transparent
        )
        subscriptionIcon.isVisible = isInSameMonth && containsSubscription
    }
}