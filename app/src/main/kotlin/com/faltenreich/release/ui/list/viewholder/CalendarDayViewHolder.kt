package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.extension.isToday
import com.faltenreich.release.ui.list.item.CalendarDayItem
import com.faltenreich.release.ui.logic.opener.DateOpener
import kotlinx.android.synthetic.main.list_item_calendar_day.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColorResource

class CalendarDayViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<CalendarDayItem>(context, R.layout.list_item_calendar_day, parent), DateOpener {
    override fun onBind(data: CalendarDayItem) {
        val (date, _, releases) = data
        val isToday = date.isToday
        val isInSameMonth = data.isInSameMonth
        val hasFavorite = releases.any { release -> release.isFavorite }

        clickable.setOnClickListener { openDate(navigationController, date) }

        dayLabel.text = date.dayOfMonth.toString()
        dayLabel.textColorResource = if (isInSameMonth) android.R.color.white else R.color.gray_dark

        container.backgroundResource = if (isInSameMonth && isToday) R.color.colorPrimaryDarker else android.R.color.transparent
        releaseIndicator.isVisible = isInSameMonth && releases.isNotEmpty()
        favoriteIcon.isVisible = isInSameMonth && hasFavorite
    }
}