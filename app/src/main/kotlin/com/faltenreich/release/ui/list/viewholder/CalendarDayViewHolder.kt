package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.extension.isToday
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.item.CalendarDateItem
import com.faltenreich.release.ui.logic.opener.DateOpener
import kotlinx.android.synthetic.main.list_item_calendar_day.*
import kotlinx.android.synthetic.main.list_item_gallery.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColorResource

class CalendarDayViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<CalendarDateItem>(context, R.layout.list_item_calendar_day, parent), DateOpener {
    override fun onBind(data: CalendarDateItem) {
        val (date, _, releases) = data
        val isToday = date.isToday
        val isInSameMonth = data.isInSameMonth

        container.backgroundResource = if (isToday) R.color.colorPrimaryDarker else android.R.color.transparent
        clickable.setOnClickListener { openDate(navigationController, date) }

        dayLabel.text = date.dayOfMonth.toString()
        dayLabel.textColorResource = if (isInSameMonth) android.R.color.white else R.color.gray_dark

        favoriteIcon.isVisible = releases.firstOrNull() != null
    }
}