package com.faltenreich.releaseradar.ui.fragment
import android.os.Bundle
import android.view.View
import com.faltenreich.releaseradar.R
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import java.util.*

class CalendarFragment : BaseFragment(R.layout.fragment_calendar), CompactCalendarView.CompactCalendarViewListener {

    private var date: LocalDate
        get() = calendarView.date
        set(value) {
            calendarView.date = value
            invalidateMonth()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        calendarView.setListener(this)
        monthButtonPrevious.setOnClickListener { date = date.minusMonths(1) }
        monthButtonNext.setOnClickListener { date = date.plusMonths(1) }
        invalidateMonth()
    }

    private fun invalidateMonth() {
        monthLabel.text = calendarView.firstDayOfCurrentMonth.toString()
    }

    override fun onDayClick(dateClicked: Date?) = Unit

    override fun onMonthScroll(firstDayOfNewMonth: Date?) = invalidateMonth()
}