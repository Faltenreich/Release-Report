package com.faltenreich.releaseradar.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.findNavController
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.ReleaseListViewModel
import com.faltenreich.releaseradar.extension.nonBlank
import com.faltenreich.releaseradar.extension.print
import com.faltenreich.releaseradar.ui.adapter.ReleaseListAdapter
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItemDecoration
import com.faltenreich.releaseradar.ui.adapter.ReleaseListLayoutManager
import com.faltenreich.skeletonlayout.applySkeleton
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.threeten.bp.LocalDate

class ReleaseListFragment : BaseFragment(R.layout.fragment_release_list) {
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

    private val firstVisibleListItemPosition: Int
        get() = listLayoutManager.findFirstVisibleItemPosition()

    private val sectionHeader: String
        get() = (listAdapter?.currentList?.getOrNull(firstVisibleListItemPosition)?.date ?: viewModel.date ?: LocalDate.now()).print()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    override fun onResume() {
        super.onResume()
        invalidatePaddingForTranslucentStatusBar()
        // FIXME: Workaround to reset shadow after onPause
        searchView.setShadow(true)
    }

    override fun onPause() {
        super.onPause()
        // FIXME: Workaround to prevent visible shadow onResume
        searchView.setShadow(false)
    }

    private fun initLayout() {
        context?.let { context ->
            searchView.setOnLogoClickListener { toolbarDelegate?.onHamburgerIconClicked() }
            searchView.doOnPreDraw {
                // FIXME: Workaround to reset shadow after onRestoreInstanceState
                searchView.setShadow(true)
            }
            searchView.setOnQueryTextListener(object : Search.OnQueryTextListener {
                override fun onQueryTextChange(newText: CharSequence?) {

                }
                override fun onQueryTextSubmit(query: CharSequence?): Boolean {
                    query?.toString()?.nonBlank?.let { findNavController().navigate(ReleaseListFragmentDirections.searchRelease(it)) }
                    return true
                }
            })

            listLayoutManager = ReleaseListLayoutManager(context, listAdapter)
            listItemDecoration = ReleaseListItemDecoration(context, R.dimen.margin_padding_size_medium, LIST_SPAN_COUNT, R.layout.list_item_release_date, R.id.releaseDateTextView) { sectionHeader }

            listView.layoutManager = listLayoutManager
            listView.addItemDecoration(listItemDecoration)
            listView.adapter = listAdapter

            todayButton.setOnClickListener { focusDate(LocalDate.now()) }
        }
    }

    private fun initData() {
        // TODO: Find way to distinguish back navigation via Navigation Components
        if (listAdapter?.itemCount == 0) {
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

    private fun invalidatePaddingForTranslucentStatusBar() {
        view?.doOnPreDraw {
            val frame = Rect()
            activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
            appbarLayout.setPadding(0, frame.top, 0, 0)
            searchView.setPadding(0, frame.top, 0, 0)
            statusBarBackground.layoutParams.height = frame.top
        }
    }

    private fun focusDate(date: LocalDate) {
        // TODO
    }

    companion object {
        private const val LIST_SPAN_COUNT = 2
        private const val LIST_SKELETON_ITEM_COUNT = 8
    }
}