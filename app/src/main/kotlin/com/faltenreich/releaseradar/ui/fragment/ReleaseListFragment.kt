package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.ReleaseListViewModel
import com.faltenreich.releaseradar.isTrue
import com.faltenreich.releaseradar.ui.adapter.ReleaseListAdapter
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItemDecoration
import com.faltenreich.releaseradar.ui.view.MonthPicker
import com.faltenreich.skeletonlayout.applySkeleton
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.threeten.bp.LocalDate
import java.util.*

class ReleaseListFragment : BaseFragment(R.layout.fragment_release_list), CompactCalendarView.CompactCalendarViewListener {
    private val viewModel by lazy { createViewModel(ReleaseListViewModel::class) }
    private val listAdapter by lazy { context?.let { context -> ReleaseListAdapter(context) } }
    private val skeleton by lazy { listView.applySkeleton(R.layout.list_item_release, itemCount = LIST_SKELETON_ITEM_COUNT, cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.card_corner_radius)?.toFloat() ?: 0f) }

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
            searchView.setShadow(false)

            listView.layoutManager = GridLayoutManager(context, LIST_SPAN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when {
                            position % 2 == 0 && listAdapter?.isSection(position + 1).isTrue() -> 2
                            else -> 1
                        }
                    }
                }
            }
            listView.addItemDecoration(ReleaseListItemDecoration(context.resources.getDimensionPixelSize(R.dimen.margin_padding_size_medium), LIST_SPAN_COUNT, listAdapter!!))
            listView.adapter = listAdapter

            invalidateMonth()
        }
    }

    private fun initData() {
        // TODO: Find way to distinguish back navigation via Navigation Components
        if (listAdapter?.itemCount == 0) {
            skeleton.showSkeleton()
            viewModel.observeReleases(this, onObserve = { releases ->
                listAdapter?.submitList(releases)
            }, onInitialLoad = {
                skeleton.showOriginal()
            })
        }
    }

    private fun invalidateMonth() {

    }

    private fun openMonthPicker() = MonthPicker.show(context, date) { selectedDate -> date = selectedDate }

    override fun onDayClick(dateClicked: Date?) = Unit

    override fun onMonthScroll(firstDayOfNewMonth: Date?) = invalidateMonth()

    companion object {
        private const val LIST_SPAN_COUNT = 2
        private const val LIST_SKELETON_ITEM_COUNT = 8
    }
}