package com.faltenreich.release.domain.release.calendar

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.base.date.print
import com.faltenreich.release.base.date.yearMonth
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.repository.ReleaseRepository
import com.faltenreich.release.domain.date.YearMonthPickerOpener
import com.faltenreich.release.domain.release.search.SearchOpener
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import kotlin.math.min

class CalendarFragment : BaseFragment(R.layout.fragment_calendar, R.menu.main),
    YearMonthPickerOpener,
    SearchOpener {
    private val viewModel by lazy { createViewModel(CalendarViewModel::class) }

    private val listAdapter by lazy { context?.let { context ->
        CalendarListAdapter(
            context
        )
    } }
    private lateinit var listLayoutManager: CalendarLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        if (!isViewCreated) {
            initData(viewModel.yearMonth ?: YearMonth.now())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.date -> {
                openYearMonthPicker(childFragmentManager, viewModel.yearMonth) { yearMonth ->
                    viewModel.yearMonth = yearMonth
                    initData(yearMonth)
                }
                true
            }
            R.id.search -> { openSearch(findNavController()); true }
            else -> false
        }
    }

    private fun initList() {
        context?.let { context ->
            listLayoutManager = CalendarLayoutManager(context, listAdapter)
            listView.layoutManager = listLayoutManager
            listView.adapter = listAdapter
            listAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    val listAdapter = listAdapter ?: return
                    val start = listAdapter.getListItemAt(positionStart)?.date ?: return
                    val end = listAdapter.getListItemAt(positionStart + itemCount - 1)?.date ?: return
                    fetchReleases(start, end)
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
    }

    private fun initData(yearMonth: YearMonth) {
        viewModel.observeReleases(yearMonth, this) { list -> listAdapter?.submitList(list) }
    }

    private fun fetchReleases(start: LocalDate, end: LocalDate) {
        ReleaseRepository.getBetween(start, end) { releases ->
            GlobalScope.launch {
                val items = listAdapter?.listItems?.filterIsInstance<CalendarDayItem>()
                releases.groupBy(Release::releaseDate).forEach { (day, releases) ->
                    items?.withIndex()?.firstOrNull { item -> item.value.date == day }?.let { indexedItem ->
                        val (index, item) = indexedItem.index to indexedItem.value
                        item.releases = releases
                        listView.post { listAdapter?.notifyItemChanged(index) }
                    }
                }
            }
        }
    }

    private fun invalidateListHeader() {
        listAdapter?.let { listAdapter ->
            val firstVisibleItemPosition = listLayoutManager.findFirstVisibleItemPosition()
            val upcomingItems = listAdapter.listItems.let { items -> items.subList(firstVisibleItemPosition, items.size) }
            val firstVisibleItem = upcomingItems.firstOrNull()
            val firstVisibleYearMonth = firstVisibleItem?.yearMonth
            val month = firstVisibleYearMonth ?: LocalDate.now().yearMonth
            headerMonthLabel.text = month.print()

            val upcomingHeaderIndex = upcomingItems.indexOfFirst { item -> item is CalendarMonthItem }
            val translationY = if (upcomingHeaderIndex >= 0) {
                val upcomingHeaderOffset = listLayoutManager.getChildAt(upcomingHeaderIndex)?.top?.toFloat() ?: 0f
                val top = if (upcomingHeaderOffset > 0f) upcomingHeaderOffset - header.height else 0f
                min(top, 0f)
            } else {
                0f
            }
            header.translationY = translationY

            viewModel.yearMonth = firstVisibleYearMonth
        }
    }
}