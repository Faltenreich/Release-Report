package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.CalendarViewModel
import com.faltenreich.release.data.viewmodel.MainViewModel
import com.faltenreich.release.extension.print
import com.faltenreich.release.extension.yearMonth
import com.faltenreich.release.ui.activity.BaseActivity
import com.faltenreich.release.ui.list.adapter.CalendarListAdapter
import com.faltenreich.release.ui.list.item.CalendarMonthItem
import com.faltenreich.release.ui.list.layoutmanager.CalendarLayoutManager
import com.faltenreich.release.ui.logic.opener.DatePickerOpener
import com.faltenreich.release.ui.view.TintAction
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import kotlin.math.min

class CalendarFragment : BaseFragment(R.layout.fragment_calendar, R.menu.calendar), DatePickerOpener {
    private val parentViewModel by lazy { (activity as BaseActivity).createViewModel(MainViewModel::class) }
    private val viewModel by lazy { createViewModel(CalendarViewModel::class) }

    private val listAdapter by lazy { context?.let { context -> CalendarListAdapter(context) } }
    private lateinit var listLayoutManager: CalendarLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel.tint = TintAction(R.color.colorPrimary)
        initList()
        initData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.date -> { openDatePicker(findNavController()); true }
            R.id.search -> { findNavController().navigate(SearchFragmentDirections.openSearch("")); true }
            R.id.filter -> { TODO() }
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

    private fun initData() {
        viewModel.observeReleases(requireContext(), this) { list -> listAdapter?.submitList(list) }
    }

    private fun invalidateListHeader() {
        listAdapter?.let { listAdapter ->
            val firstVisibleItemPosition = listLayoutManager.findFirstVisibleItemPosition()
            val upcomingItems = listAdapter.listItems.let { items -> items.subList(firstVisibleItemPosition, items.size) }
            val firstVisibleItem = upcomingItems.firstOrNull()
            val firstVisibleYearMonth = firstVisibleItem?.yearMonth
            val month = firstVisibleYearMonth ?: LocalDate.now().yearMonth
            headerMonthLabel.text = month.print(context)

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