package com.faltenreich.release.domain.release.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.faltenreich.release.R
import com.faltenreich.release.base.date.LocalDateProgression
import com.faltenreich.release.base.date.Now
import com.faltenreich.release.base.date.atEndOfWeek
import com.faltenreich.release.base.date.atStartOfWeek
import com.faltenreich.release.domain.preference.UserPreferences
import kotlinx.android.synthetic.main.view_weekday.view.*
import org.threeten.bp.format.TextStyle

class WeekView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {
    init {
        val padding = context.resources.getDimension(R.dimen.margin_padding_size_xsmall).toInt()
        setPadding(0, padding, 0, padding)
        orientation = HORIZONTAL

        val date = Now.localDate()
        val startOfWeek = date.atStartOfWeek
        val endOfWeek = date.atEndOfWeek

        LocalDateProgression(startOfWeek, endOfWeek).forEach { day ->
            val dayOfWeek = day.dayOfWeek
            val view = LayoutInflater.from(context).inflate(R.layout.view_weekday, null)
            view.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            view.label.text = dayOfWeek.getDisplayName(TextStyle.NARROW, UserPreferences.locale).toUpperCase()
            addView(view)
        }
    }
}