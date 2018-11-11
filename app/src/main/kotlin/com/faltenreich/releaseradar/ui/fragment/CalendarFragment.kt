package com.faltenreich.releaseradar.ui.fragment
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.dao.ReleaseDao
import com.faltenreich.releaseradar.data.printMonth
import com.faltenreich.releaseradar.data.printYear
import com.faltenreich.releaseradar.ui.adapter.ReleaseListAdapter
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import java.util.*

class CalendarFragment : BaseFragment(R.layout.fragment_calendar), CompactCalendarView.CompactCalendarViewListener {

    private val listAdapter by lazy { context?.let { context -> ReleaseListAdapter(context) } }

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

        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = listAdapter

        ReleaseDao.getAll(onSuccess = { releases ->
            listAdapter?.apply {
                addAll(releases)
                notifyDataSetChanged()
            }
        })
    }

    private fun invalidateMonth() {
        calendarView.date.let { date ->
            monthLabel.text = "${date.printMonth()} ${date.printYear()}"
        }
    }

    override fun onDayClick(dateClicked: Date?) = Unit

    override fun onMonthScroll(firstDayOfNewMonth: Date?) = invalidateMonth()
}