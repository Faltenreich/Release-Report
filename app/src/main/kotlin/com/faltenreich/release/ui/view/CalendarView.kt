package com.faltenreich.release.ui.view

import android.content.Context
import android.util.AttributeSet
import com.faltenreich.release.extension.date
import com.faltenreich.release.extension.localDate
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import org.threeten.bp.LocalDate

class CalendarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CompactCalendarView(context, attributeSet, defStyleAttr) {

    var date: LocalDate
        get() = firstDayOfCurrentMonth.localDate
        set(value) = setCurrentDate(value.date)
}