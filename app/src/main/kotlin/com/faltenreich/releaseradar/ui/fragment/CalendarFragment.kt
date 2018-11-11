package com.faltenreich.releaseradar.ui.fragment
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.dao.ReleaseDao
import com.faltenreich.releaseradar.data.printMonth
import com.faltenreich.releaseradar.data.printYear
import com.faltenreich.releaseradar.ui.adapter.ReleaseListAdapter
import com.faltenreich.releaseradar.ui.view.MonthPicker
import com.faltenreich.skeletonlayout.applySkeleton
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.view_calendar_header.*
import org.threeten.bp.LocalDate
import java.util.*

class CalendarFragment : BaseFragment(R.layout.fragment_calendar), CompactCalendarView.CompactCalendarViewListener {

    private val listAdapter by lazy { context?.let { context -> ReleaseListAdapter(context) } }
    private val skeleton by lazy { listView.applySkeleton(R.layout.list_item_release) }

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

        monthLabel.setOnClickListener { openMonthPicker() }
        monthButtonPrevious.setOnClickListener { date = date.minusMonths(1) }
        monthButtonNext.setOnClickListener { date = date.plusMonths(1) }
        invalidateMonth()

        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = listAdapter

        skeleton.showSkeleton()

        ReleaseDao.getAll(onSuccess = { releases ->
            listAdapter?.apply {
                addAll(releases)
                notifyDataSetChanged()
                skeleton.showOriginal()
            }
        })
    }

    private fun invalidateMonth() {
        calendarView.date.let { date ->
            monthLabel.text = "%s %s".format(date.printMonth(), date.printYear())
        }
    }

    private fun openMonthPicker() = MonthPicker.show(context, date) { selectedDate -> date = selectedDate }

    override fun onDayClick(dateClicked: Date?) = Unit

    override fun onMonthScroll(firstDayOfNewMonth: Date?) = invalidateMonth()
}