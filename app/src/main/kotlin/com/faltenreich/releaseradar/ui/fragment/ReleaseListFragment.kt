package com.faltenreich.releaseradar.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.print
import com.faltenreich.releaseradar.data.viewmodel.ReleaseListViewModel
import com.faltenreich.releaseradar.ui.adapter.ReleaseListAdapter
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItemDecoration
import com.faltenreich.releaseradar.ui.adapter.ReleaseListLayoutManager
import com.faltenreich.releaseradar.ui.view.MonthPicker
import com.faltenreich.skeletonlayout.applySkeleton
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.threeten.bp.LocalDate
import java.util.*

class ReleaseListFragment : BaseFragment(R.layout.fragment_release_list), CompactCalendarView.CompactCalendarViewListener {
    private val viewModel by lazy { createViewModel(ReleaseListViewModel::class) }
    
    private val listAdapter by lazy { context?.let { context -> ReleaseListAdapter(context) } }
    private lateinit var listLayoutManager: ReleaseListLayoutManager
    private lateinit var listItemDecoration: ReleaseListItemDecoration

    private val skeleton by lazy {
        listView.applySkeleton(R.layout.list_item_release,
            itemCount = LIST_SKELETON_ITEM_COUNT,
            maskColor = ContextCompat.getColor(context!!, R.color.colorPrimary),
            shimmerColor = ContextCompat.getColor(context!!, R.color.blue_gray),
            cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.card_corner_radius)?.toFloat() ?: 0f)
    }

    private var date: LocalDate = LocalDate.now()
        set(value) {
            field = value
            invalidateMonth()
        }

    private val firstVisibleListItemPosition: Int
        get() = listLayoutManager.findFirstVisibleItemPosition()

    private val sectionHeader: String?
        get() = listAdapter?.currentList?.getOrNull(firstVisibleListItemPosition)?.date?.print()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    override fun onResume() {
        super.onResume()
        invalidatePaddingForTranslucentStatusbar()
    }

    private fun initLayout() {
        context?.let { context ->
            searchView.setOnLogoClickListener { toolbarDelegate?.onHamburgerIconClicked() }
            searchView.setShadow(false)

            listLayoutManager = ReleaseListLayoutManager(context, listAdapter)
            listItemDecoration = ReleaseListItemDecoration(context, R.dimen.margin_padding_size_medium, LIST_SPAN_COUNT, R.layout.list_item_release_date, R.id.releaseDateTextView) { sectionHeader }

            listView.layoutManager = listLayoutManager
            listView.addItemDecoration(listItemDecoration)
            listView.adapter = listAdapter

            invalidateMonth()
        }
    }

    private fun initData() {
        // TODO: Find way to distinguish back navigation via Navigation Components
        if (listAdapter?.itemCount == 0) {
            listItemDecoration.showOverlay = false
            skeleton.showSkeleton()

            viewModel.observeReleases(this, onObserve = { releases ->
                listAdapter?.submitList(releases)

            }, onInitialLoad = {
                listItemDecoration.showOverlay = true
                skeleton.showOriginal()
            })
        }
    }

    private fun invalidatePaddingForTranslucentStatusbar() {
        view?.doOnPreDraw {
            val frame = Rect()
            activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
            appbarLayout.setPadding(0, frame.top, 0, 0)
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