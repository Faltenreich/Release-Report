package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.data.provider.Dateable
import com.faltenreich.release.data.viewmodel.CalendarViewModel
import com.faltenreich.release.data.viewmodel.MainViewModel
import com.faltenreich.release.extension.*
import com.faltenreich.release.ui.activity.BaseActivity
import com.faltenreich.release.ui.list.adapter.CalendarListAdapter
import com.faltenreich.release.ui.list.item.CalendarDayListItem
import com.faltenreich.release.ui.list.item.CalendarMonthListItem
import com.faltenreich.release.ui.list.item.CalendarWeekDayListItem
import com.faltenreich.release.ui.list.layoutmanager.CalendarLayoutManager
import com.faltenreich.release.ui.view.TintAction
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.threeten.bp.LocalDate

class CalendarFragment : BaseFragment(R.layout.fragment_calendar), Dateable {
    private val parentViewModel by lazy { (activity as BaseActivity).createViewModel(MainViewModel::class) }
    private val viewModel by lazy { createViewModel(CalendarViewModel::class) }

    private val listAdapter by lazy { context?.let { context -> CalendarListAdapter(context) } }
    private lateinit var listLayoutManager: GridLayoutManager

    override val date: LocalDate?
        get() = viewModel.date

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel.tint = TintAction(R.color.colorPrimary)
        initList()
        initData()
    }

    private fun initList() {
        context?.let { context ->
            listLayoutManager = CalendarLayoutManager(context, listAdapter)
            listView.layoutManager = listLayoutManager
            listView.adapter = listAdapter
        }
    }

    private fun initData() {
        viewModel.observeDate(this) { invalidateData() }
        viewModel.observeReleases(this) { invalidateData() }
        viewModel.date = LocalDate.now()
    }

    private fun invalidateData() {
        val context = context ?: return
        val date = viewModel.date ?: return
        val startOfFirstWeek = date.atStartOfMonth.atStartOfWeek(context)
        val endOfFirstWeek = startOfFirstWeek.atEndOfWeek(context)
        val endOfLastWeek = date.atEndOfMonth.atEndOfWeek(context)
        val monthItem = CalendarMonthListItem(startOfFirstWeek)
        val weekDayItems = LocalDateProgression(startOfFirstWeek, endOfFirstWeek).map { day -> CalendarWeekDayListItem(day) }
        val dayItems = LocalDateProgression(startOfFirstWeek, endOfLastWeek).map { day ->
            val releases = viewModel.releases?.filter { release -> (release.releaseDate == day).isTrue }
            CalendarDayListItem(day, releases ?: listOf(), day.month == date.month)
        }
        val items = listOf(monthItem).plus(weekDayItems.plus(dayItems))
        listAdapter?.apply {
            removeListItems()
            addListItems(items)
            notifyDataSetChanged()
        }
    }
}