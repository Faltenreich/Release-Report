package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.ReleaseListViewModel
import com.faltenreich.releaseradar.extension.isTrue
import com.faltenreich.releaseradar.extension.nonBlank
import com.faltenreich.releaseradar.extension.print
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseListAdapter
import com.faltenreich.releaseradar.ui.list.decoration.ReleaseListItemDecoration
import com.faltenreich.releaseradar.ui.list.layoutmanager.ReleaseListLayoutManager
import com.faltenreich.releaseradar.ui.list.behavior.SlideOutBehavior
import com.faltenreich.skeletonlayout.applySkeleton
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_release_list.*
import kotlinx.android.synthetic.main.list_item_release_date.*
import org.threeten.bp.LocalDate

class ReleaseListFragment : BaseFragment(R.layout.fragment_release_list) {

    private val viewModel by lazy { createViewModel(ReleaseListViewModel::class) }
    private val searchable by lazy { SearchableObserver() }
    
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

    private val sectionHeader: String
        get() = (listAdapter?.currentList?.getOrNull(firstVisibleListItemPosition)?.date ?: viewModel.date ?: LocalDate.now()).print()

    private var showTodayButton: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(searchable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    override fun onResume() {
        super.onResume()
        searchView.logo = Search.Logo.HAMBURGER_ARROW
    }

    private fun initLayout() {
        searchable.properties = SearchableProperties(this, searchView, appbarLayout, statusBarBackground)
        initSearch()
        initList()
        initTodayButton()
    }

    private fun initSearch() {
        searchView.setOnLogoClickListener { toolbarDelegate?.onHamburgerIconClicked() }
        searchView.setOnQueryTextListener(object : Search.OnQueryTextListener {
            override fun onQueryTextChange(newText: CharSequence?) = Unit
            override fun onQueryTextSubmit(query: CharSequence?): Boolean {
                query?.toString()?.nonBlank?.let {
                    searchView.logo = Search.Logo.ARROW
                    findNavController().navigate(ReleaseListFragmentDirections.searchRelease(it))
                }
                return true
            }
        })
    }

    private fun initList() {
        context?.let { context ->
            listLayoutManager = ReleaseListLayoutManager(context, listAdapter)
            listItemDecoration =
                    ReleaseListItemDecoration(context, R.dimen.margin_padding_size_medium, LIST_SPAN_COUNT)

            listView.layoutManager = listLayoutManager
            listView.addItemDecoration(listItemDecoration)
            listView.adapter = listAdapter
        }
    }

    private fun initTodayButton() {
        context?.let { context ->
            todayButton.setOnClickListener { focusDate(LocalDate.now()) }
            todayButton.doOnPreDraw { todayButton.translationY = todayButton.height.toFloat() + (todayButton.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin }

            todayButtonBehavior = SlideOutBehavior(context)
            (todayButton.layoutParams as CoordinatorLayout.LayoutParams).behavior = todayButtonBehavior
            toggleTodayButton(false)

            listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val isScrollingUp = dy < 0
                    invalidateTodayButton(isScrollingUp)
                    releaseDateTextView.text = sectionHeader
                }
            })
        }
    }

    private fun initData(force: Boolean = false) {
        // TODO: Find way to distinguish back navigation via Navigation Components
        if (force || listAdapter?.itemCount == 0) {
            listItemDecoration.isSkeleton = true
            skeleton.showSkeleton()

            viewModel.observeReleases(this, onObserve = { releases ->
                listAdapter?.submitList(releases)

            }, onInitialLoad = {
                listItemDecoration.isSkeleton = false
                skeleton.showOriginal()
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

        } ?: initData()
    }

    companion object {
        private const val LIST_SPAN_COUNT = 2
        private const val LIST_SKELETON_ITEM_COUNT = 8
        private const val TODAY_BUTTON_TOGGLE_DURATION = 200L
    }
}