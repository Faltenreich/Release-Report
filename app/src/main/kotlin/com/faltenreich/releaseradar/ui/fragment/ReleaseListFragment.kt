package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.CalendarViewModel
import com.faltenreich.releaseradar.ui.adapter.EntityDiffCallback
import com.faltenreich.releaseradar.ui.adapter.ReleaseListAdapter
import com.faltenreich.releaseradar.ui.adapter.SpaceOnEachSideItemDecoration
import com.faltenreich.releaseradar.ui.view.MonthPicker
import com.faltenreich.skeletonlayout.applySkeleton
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.threeten.bp.LocalDate
import java.util.*

class ReleaseListFragment : BaseFragment(R.layout.fragment_release_list), CompactCalendarView.CompactCalendarViewListener {
    private val viewModel by lazy { createViewModel(CalendarViewModel::class) }
    private val listAdapter by lazy { context?.let { context -> ReleaseListAdapter(context) } }
    private val skeleton by lazy { listView.applySkeleton(R.layout.list_item_release, 9, cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.card_corner_radius)?.toFloat() ?: 0f) }

    private var date: LocalDate = LocalDate.now()
        set(value) {
            field = value
            invalidateMonth()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    private fun initLayout() {
        context?.let { context ->
            searchView.setOnLogoClickListener { toolbarDelegate?.onHamburgerIconClicked() }

            listView.layoutManager = GridLayoutManager(context, 2)
            listView.addItemDecoration(SpaceOnEachSideItemDecoration(context, R.dimen.margin_padding_size_small))
            listView.adapter = listAdapter

            invalidateMonth()
        }
    }

    private fun initData() {
        skeleton.showSkeleton()
        viewModel.observeReleases(this) { releases ->
            listAdapter?.apply {
                val diff = DiffUtil.calculateDiff(EntityDiffCallback(items, releases))
                clear()
                addAll(releases)
                diff.dispatchUpdatesTo(this)
            }
            skeleton.showOriginal()
        }
    }

    private fun invalidateMonth() {

    }

    private fun openMonthPicker() = MonthPicker.show(context, date) { selectedDate -> date = selectedDate }

    override fun onDayClick(dateClicked: Date?) = Unit

    override fun onMonthScroll(firstDayOfNewMonth: Date?) = invalidateMonth()
}