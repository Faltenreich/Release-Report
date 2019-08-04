package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.ReleaseListViewModel
import com.faltenreich.release.extension.asLocalDate
import com.faltenreich.release.extension.print
import com.faltenreich.release.ui.list.adapter.ReleaseListAdapter
import com.faltenreich.release.ui.list.decoration.ItemDecoration.Companion.SPACING_RES_DEFAULT
import com.faltenreich.release.ui.list.decoration.ReleaseListItemDecoration
import com.faltenreich.release.ui.logic.opener.DatePickerOpener
import com.faltenreich.release.ui.logic.opener.SearchOpener
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.android.synthetic.main.fragment_release_list.*
import kotlinx.android.synthetic.main.view_empty.*
import org.jetbrains.anko.textResource
import org.threeten.bp.LocalDate

class ReleaseListFragment : BaseFragment(R.layout.fragment_release_list, R.menu.main), DatePickerOpener, SearchOpener {
    private val viewModel by lazy { createViewModel(ReleaseListViewModel::class) }
    private val date: LocalDate? by lazy { arguments?.let { arguments -> ReleaseListFragmentArgs.fromBundle(arguments).date?.asLocalDate } }

    private val listAdapter by lazy { context?.let { context -> ReleaseListAdapter(context) } }
    private lateinit var listLayoutManager: LinearLayoutManager
    private val listSpacing by lazy { context?.resources?.getDimension(SPACING_RES_DEFAULT)?.toInt() ?: 0 }

    private val skeleton by lazy {
        listView.applySkeleton(R.layout.list_item_release_image,
            itemCount = 6,
            maskColor = ContextCompat.getColor(context!!, R.color.colorPrimary),
            shimmerColor = ContextCompat.getColor(context!!, R.color.blue_gray),
            cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.card_corner_radius)?.toFloat() ?: 0f)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.date -> { openDatePicker(); true }
            R.id.search -> { openSearch(); true }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        if (!isViewCreated) {
            initData(date ?: LocalDate.now())
        }
    }

    private fun initLayout() {
        context?.let { context ->
            toolbar.setNavigationOnClickListener { finish() }

            listLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            listView.layoutManager = listLayoutManager
            listView.adapter = listAdapter
            listView.addItemDecoration(ReleaseListItemDecoration(context, listSpacing))

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

    private fun initData(date: LocalDate) {
        skeleton.showSkeleton()
        viewModel.observeReleases(date, this, onObserve = { releases ->
            listAdapter?.submitList(releases)
        }, afterLoadInitial = { size ->
            skeleton.showOriginal()
            emptyView.isVisible = size == 0
            emptyLabel.textResource = R.string.nothing_found
            scrollTo(date)
        })
    }

    private fun invalidateListHeader() {
        val firstVisibleListItemPosition = listLayoutManager.findFirstVisibleItemPosition()
        val firstVisibleListItem = listAdapter?.currentList?.getOrNull(firstVisibleListItemPosition)
        val currentDate = firstVisibleListItem?.date ?: viewModel.date ?: LocalDate.now()
        toolbar.title = currentDate?.print(context)
    }

    private fun scrollTo(date: LocalDate) {
        listAdapter?.getFirstPositionForDate(date)?.let { position ->
            listView.stopScroll()
            // Skip header since date is being displayed in Toolbar as well
            listLayoutManager.scrollToPositionWithOffset(position + 1, listSpacing)
        }
    }

    private fun openDatePicker() {
        openDatePicker(childFragmentManager) { date -> initData(date) }
    }

    private fun openSearch() {
        openSearch(findNavController())
    }
}