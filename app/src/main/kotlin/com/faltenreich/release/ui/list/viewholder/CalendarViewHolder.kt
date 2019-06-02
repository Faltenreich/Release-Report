package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.item.CalendarListItem
import kotlinx.android.synthetic.main.list_item_calendar.*
import org.jetbrains.anko.textColorResource

class CalendarViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<CalendarListItem>(context, R.layout.list_item_calendar, parent) {

    override fun onBind(data: CalendarListItem) {
        dayLabel.text = data.date.dayOfMonth.toString()
        dayLabel.textColorResource = if (data.isInSameMonth) android.R.color.white else R.color.gray
        data.releases.firstOrNull()?.imageUrlForThumbnail?.let { imageUrl ->
            imageView.setImageAsync(imageUrl, context.screenSize.x / 2 )
            shadow.isVisible = true
        } ?: run {
            imageView.setImageResource(android.R.color.transparent)
            shadow.isVisible = false
        }
    }
}