package com.faltenreich.release.domain.release.calendar

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.base.date.Now
import com.faltenreich.release.base.date.print
import com.faltenreich.release.base.date.yearMonth
import com.faltenreich.release.base.primitive.takeIfNotEmpty
import com.faltenreich.release.domain.date.YearMonthPickerOpener
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import kotlin.math.min

class CalendarFragment : BaseFragment(R.layout.fragment_calendar, R.menu.main), YearMonthPickerOpener{

    private val viewModel by viewModels<CalendarViewModel>()

    private lateinit var listAdapter: CalendarListAdapter
    private lateinit var listLayoutManager: CalendarLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        if (!isViewCreated) {
            initData(viewModel.yearMonth ?: YearMonth.now())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.date -> { openYearMonthPicker(); true }
            else -> false
        }
    }

    private fun init() {
        listAdapter = CalendarListAdapter(requireContext())
    }

    private fun initLayout() {
        val context = context ?: return

        listLayoutManager = CalendarLayoutManager(context, listAdapter)
        listView.layoutManager = listLayoutManager
        listView.adapter = listAdapter

        listAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val start = listAdapter.getListItemAt(positionStart)?.date ?: return
                val end = listAdapter.getListItemAt(positionStart + itemCount - 1)?.date ?: return
                fetchCalendarEvents(start, end)
            }
        })

        listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isAdded) {
                    invalidateListHeader()
                }
            }
        })
    }

    private fun initData(yearMonth: YearMonth) {
        viewModel.observeReleases(yearMonth, this) { list -> listAdapter.submitList(list) }
    }

    private fun fetchCalendarEvents(start: LocalDate, end: LocalDate) {
        lifecycleScope.launch(Dispatchers.IO) {
            val calendarEvents = viewModel.getCalendarEvents(start, end)
            val items = listAdapter.listItems.filterIsInstance<CalendarDayItem>()
            val indexes = calendarEvents.mapNotNull { calendarEvent ->
                val indexedItem = items.withIndex().firstOrNull { item ->
                    item.value.date == calendarEvent.date && item.value.isInSameMonth
                } ?: return@mapNotNull null
                val (index, item) = indexedItem.index to indexedItem.value
                item.calendarEvent = calendarEvent
                return@mapNotNull index
            }
            lifecycleScope.launch(Dispatchers.Main) {
                // Workaround: notifyItemChanged() does not update all items if called frequently
                (indexes.minOrNull() to indexes.maxOrNull()).takeIfNotEmpty()?.let { (min, max) ->
                    listAdapter.notifyItemRangeChanged(min, max)
                }
            }
        }
    }

    private fun invalidateListHeader() {
        val firstVisibleItemPosition = listLayoutManager.findFirstVisibleItemPosition()
        val upcomingItems = listAdapter.listItems.let { items ->
            items.subList(
                firstVisibleItemPosition,
                items.size
            )
        }
        val firstVisibleItem = upcomingItems.firstOrNull()
        val firstVisibleYearMonth = firstVisibleItem?.yearMonth
        val month = firstVisibleYearMonth ?: Now.localDate().yearMonth
        headerMonthLabel.text = month.print()

        val upcomingHeaderIndex = upcomingItems.indexOfFirst { item -> item is CalendarMonthItem }
        val translationY = if (upcomingHeaderIndex >= 0) {
            val upcomingHeaderOffset =
                listLayoutManager.getChildAt(upcomingHeaderIndex)?.top?.toFloat() ?: 0f
            val top = if (upcomingHeaderOffset > 0f) upcomingHeaderOffset - header.height else 0f
            min(top, 0f)
        } else {
            0f
        }
        header.translationY = translationY

        viewModel.yearMonth = firstVisibleYearMonth
    }

    private fun openYearMonthPicker() {
        openYearMonthPicker(childFragmentManager, viewModel.yearMonth) { yearMonth ->
            viewModel.yearMonth = yearMonth
            initData(yearMonth)
        }
    }
}