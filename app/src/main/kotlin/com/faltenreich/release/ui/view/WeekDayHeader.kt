package com.faltenreich.release.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.faltenreich.release.R
import com.faltenreich.release.extension.LocalDateProgression
import com.faltenreich.release.extension.atEndOfWeek
import com.faltenreich.release.extension.atStartOfWeek
import com.faltenreich.release.extension.locale
import kotlinx.android.synthetic.main.view_weekday.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle

class WeekDayHeader @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    init {
        orientation = HORIZONTAL

        val date = LocalDate.now()
        val startOfWeek = date.atStartOfWeek(context)
        val endOfWeek = date.atEndOfWeek(context)

        LocalDateProgression(startOfWeek, endOfWeek).forEach { day ->
            val dayOfWeek = day.dayOfWeek
            val padding = context.resources.getDimension(R.dimen.margin_padding_size_xsmall).toInt()
            val view = LayoutInflater.from(context).inflate(R.layout.view_weekday, null)
            view.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            view.label.text = dayOfWeek.getDisplayName(TextStyle.SHORT, context.locale).toUpperCase()
            view.setPadding(0, padding, 0, padding)
            addView(view)
        }
    }
}