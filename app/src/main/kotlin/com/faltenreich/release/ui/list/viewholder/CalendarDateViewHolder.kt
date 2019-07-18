package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.faltenreich.release.NavigationGraphDirections
import com.faltenreich.release.R
import com.faltenreich.release.extension.asString
import com.faltenreich.release.extension.isToday
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.item.CalendarDateItem
import kotlinx.android.synthetic.main.list_item_calendar_day.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColorResource

class CalendarDateViewHolder(
    context: Context,
    parent: ViewGroup
) : CalendarViewHolder<CalendarDateItem>(
    context,
    R.layout.list_item_calendar_day,
    parent
) {

    override fun onBind(data: CalendarDateItem) {
        container.setOnClickListener { openItem(data) }

        val date = data.date
        val isToday = date.isToday

        dayLabel.text = data.date.dayOfMonth.toString()
        dayLabel.textColorResource = if (data.isInSameMonth) android.R.color.white else R.color.gray_dark
        // TODO: container.backgroundResource = if (isToday) R.color.colorPrimary else android.R.color.transparent

        data.releases.firstOrNull()?.imageUrlForThumbnail?.takeIf { data.isInSameMonth }?.let { imageUrl ->
            dayLabel.isVisible = false
            imageView.setImageAsync(imageUrl, context.screenSize.x / 2 )
        } ?: run {
            dayLabel.isVisible = true
            imageView.imageResource = android.R.color.transparent
        }
    }

    private fun openItem(item: CalendarDateItem) {
        itemView.findNavController().navigate(NavigationGraphDirections.openDate(item.date.asString))
    }
}