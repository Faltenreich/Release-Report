package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.extension.isToday
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.item.CalendarDayListItem
import kotlinx.android.synthetic.main.list_item_calendar_day.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColorResource
import org.threeten.bp.LocalDate

class CalendarDayViewHolder(context: Context, parent: ViewGroup) : CalendarViewHolder<CalendarDayListItem>(context, R.layout.list_item_calendar_day, parent) {

    override fun onBind(data: CalendarDayListItem) {
        overlay.setOnClickListener { openDay(data.date) }

        val date = data.date
        val isToday = date.isToday

        dayLabel.text = data.date.dayOfMonth.toString()
        dayLabel.textColorResource = if (data.isInSameMonth) android.R.color.white else R.color.gray_dark
        // TODO: container.backgroundResource = if (isToday) R.color.colorPrimary else android.R.color.transparent

        data.releases.firstOrNull()?.imageUrlForThumbnail?.let { imageUrl ->
            dayLabel.isVisible = false
            imageView.setImageAsync(imageUrl, context.screenSize.x / 2 )
        } ?: run {
            dayLabel.isVisible = true
            imageView.imageResource = android.R.color.transparent
        }
    }

    private fun openDay(day: LocalDate) {

    }
}