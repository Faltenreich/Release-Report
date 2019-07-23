package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.CalendarViewModel
import com.faltenreich.release.extension.print
import com.faltenreich.release.extension.yearMonth
import com.faltenreich.release.ui.list.adapter.CalendarListAdapter
import com.faltenreich.release.ui.list.decoration.CalendarItemDecoration
import com.faltenreich.release.ui.list.item.CalendarMonthItem
import com.faltenreich.release.ui.list.layoutmanager.CalendarLayoutManager
import com.faltenreich.release.ui.logic.opener.SearchOpener
import com.faltenreich.release.ui.logic.opener.YearMonthPickerOpener
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import kotlin.math.min

// TODO: Add skeleton
class CalendarFragment : BaseFragment(R.layout.fragment_calendar, R.menu.main), YearMonthPickerOpener, SearchOpener {
    private val viewModel by lazy { createViewModel(CalendarViewModel::class) }

    private val listAdapter by lazy { context?.let { context -> CalendarListAdapter(context) } }
    private lateinit var listLayoutManager: CalendarLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initData(viewModel.yearMonth ?: YearMonth.now())
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