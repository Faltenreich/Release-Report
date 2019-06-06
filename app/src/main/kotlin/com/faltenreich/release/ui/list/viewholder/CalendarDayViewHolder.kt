package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.extension.isToday
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.item.CalendarDayListItem
import kotlinx.android.synthetic.main.list_item_calendar.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColorResource
import org.threeten.bp.LocalDate

class CalendarDayViewHolder(context: Context, parent: ViewGroup) : CalendarViewHolder<CalendarDayListItem>(context, R.layout.list_item_calendar, parent) {
    private val borderRadius = context.resources.getDimension(R.dimen.margin_padding_size_xsmall).toInt()

    override fun onBind(data: CalendarDayListItem) {
        val date = data.date
        val isToday = date.isToday

        container.setOnClickListener { openDay(data.date) }
        dayLabel.text = data.date.dayOfMonth.toString()
        dayLabel.textColorResource = if (data.isInSameMonth) android.R.color.white else R.color.gray_dark
        container.backgroundResource = if (isToday) R.drawable.background_primary_round else android.R.color.transparent
        imageView.borderWidth = if (isToday) borderRadius else 0

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