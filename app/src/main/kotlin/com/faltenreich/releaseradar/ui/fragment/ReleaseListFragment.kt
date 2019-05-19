package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.ReleaseListViewModel
import com.faltenreich.releaseradar.extension.isTrue
import com.faltenreich.releaseradar.extension.print
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseListAdapter
import com.faltenreich.releaseradar.ui.list.behavior.SlideOutBehavior
import com.faltenreich.releaseradar.ui.list.decoration.ReleaseListItemDecoration
import com.faltenreich.releaseradar.ui.list.layoutmanager.ReleaseListLayoutManager
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.jetbrains.anko.support.v4.runOnUiThread
import org.threeten.bp.LocalDate
import kotlin.math.min

class ReleaseListFragment : BaseFragment(R.layout.fragment_release_list) {
    private val viewModel by lazy { createViewModel(ReleaseListViewModel::class) }
    
    private val listAdapter by lazy { context?.let { context -> ReleaseListAdapter(context) } }
    private lateinit var listLayoutManager: ReleaseListLayoutManager
    private lateinit var listItemDecoration: ReleaseListItemDecoration
    private lateinit var todayButtonBehavior: SlideOutBehavior

    private val skeleton by lazy {
        listView.applySkeleton(R.layout.list_item_release,
            itemCount = LIST_SKELETON_ITEM_COUNT,
            maskColor = ContextCompat.getColor(context!!, R.color.colorPrimary),
            shimmerColor = ContextCompat.getColor(context!!, R.color.blue_gray),
            cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.card_corner_radius)?.toFloat() ?: 0f)
    }

    private val firstVisibleListItemPosition: Int
        get() = listLayoutManager.findFirstVisibleItemPosition()

    private var showTodayButton: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initTodayButton()
        initData(LocalDate.now())
    }

    private fun initList() {
        context?.let { context ->
            listLayoutManager = ReleaseListLayoutManager(context, listAdapter)
            listItemDecoration = ReleaseListItemDecoration(context, R.dimen.margin_padding_size_medium, LIST_SPAN_COUNT)

            listView.layoutManager = listLayoutManager
            listView.addItemDecoration(listItemDecoration)
            listView.adapter = listAdapter

            listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (isAdded) {
                        val isScrollingUp = dy < 0
                        invalidateTodayButton(isScrollingUp)
                        invalidateListHeader()
                    }
                }
            })

            headerDateButton.setOnClickListener { openDatePicker() }
        }
    }

    private fun initTodayButton() {
        context?.let { context ->
            todayButton.setOnClickListener { focusDate(LocalDate.now()) }
            todayButton.doOnPreDraw { todayButton.translationY = todayButton.height.toFloat() + (todayButton.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin }

            todayButtonBehavior = SlideOutBehavior(context)
            todayButtonBehavior.isEnabled = false
            // FIXME: todayButton.layoutParams is of LinearLayout.LayoutParams
            (todayButton.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior = todayButtonBehavior
            toggleTodayButton(false)
        }
    }

    private fun initData(date: LocalDate, force: Boolean = false) {
        // TODO: Find way to distinguish back navigation via Navigation Components
        val itemCount = listAdapter?.itemCount ?: 0
        if (force || itemCount == 0) {
            skeleton.showSkeleton()
            listItemDecoration.isSkeleton = true

            viewModel.observeReleases(date, this, onObserve = { releases ->
                listAdapter?.submitList(releases)
            }, onInitialLoad = { count ->
                runOnUiThread {
                    skeleton.showOriginal()
                    listItemDecoration.isSkeleton = false
                    listEmptyView.isVisible = count == 0
                }
            })
        }
    }

    private fun invalidateTodayButton(commit: Boolean) {
        val show = listAdapter?.let { listAdapter ->
            val today = LocalDate.now()
            val isBeforeToday = listAdapter.getListItemAt(listLayoutManager.findLastVisibleItemPosition())?.date?.isBefore(today).isTrue
            val isAfterToday = listAdapter.getListItemAt(listLayoutManager.findFirstVisibleItemPosition())?.date?.isAfter(today).isTrue
            isBeforeToday || isAfterToday
        } ?: false
        if (showTodayButton != show) {
            showTodayButton = show
            todayButtonBehavior.isEnabled = show
            if (commit) {
                toggleTodayButton(true)
            }
        }
    }

    private fun invalidateListHeader() {
        val firstVisibleListItemPosition = firstVisibleListItemPosition
        val firstVisibleListItem = listAdapter?.currentList?.getOrNull(firstVisibleListItemPosition)
        val currentDate = firstVisibleListItem?.date ?: viewModel.date ?: LocalDate.now()
        headerDateTextView.text = currentDate?.print(context)

        val secondVisibleListItem = listAdapter?.currentList?.getOrNull(firstVisibleListItemPosition +  1)
        val translateHeader = secondVisibleListItem?.release == null
        // TODO
        if (false) {
            val top = listLayoutManager.getChildAt(1)?.top ?: 0
            val translationY = min(top, header.height)
            header.translationY = translationY.toFloat()
        }
    }

    private fun toggleTodayButton(animated: Boolean) {
        val translationY = if (showTodayButton) 0f else todayButton.height + (todayButton.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin.toFloat()
        if (animated) {
            todayButton.animate().translationY(translationY).setDuration(TODAY_BUTTON_TOGGLE_DURATION).start()
        } else {
            todayButton.translationY = translationY
        }
    }

    private fun focusDate(date: LocalDate) {
        listAdapter?.getFirstPositionForDate(date)?.let { position ->
            listView.stopScroll()
            listLayoutManager.scrollToPositionWithOffset(position, 0)

            val show = date != LocalDate.now()
            showTodayButton = show
            todayButtonBehavior.isEnabled = show
            toggleTodayButton(true)

        } ?: initData(date, true)
    }

    private fun openDatePicker() {
        val date = listAdapter?.getListItemAt(listLayoutManager.findFirstVisibleItemPosition())?.date ?: LocalDate.now()
        DatePickerFragment.newInstance(date) { newDate -> newDate?.let { focusDate(newDate) } }.show(fragmentManager, null)
    }

    companion object {
        private const val LIST_SPAN_COUNT = 2
        private const val LIST_SKELETON_ITEM_COUNT = 8
        private const val TODAY_BUTTON_TOGGLE_DURATION = 200L
    }
}