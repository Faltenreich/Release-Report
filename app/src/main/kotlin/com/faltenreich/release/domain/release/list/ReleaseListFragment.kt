package com.faltenreich.release.domain.release.list

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.base.date.Now
import com.faltenreich.release.base.date.asLocalDate
import com.faltenreich.release.base.date.print
import com.faltenreich.release.domain.date.DatePickerOpener
import com.faltenreich.release.domain.date.DateProvider
import com.faltenreich.release.domain.release.DateProviderNavigation
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.recyclerview.decoration.ItemDecoration.Companion.SPACING_RES_DEFAULT
import com.faltenreich.release.framework.skeleton.SkeletonFactory
import kotlinx.android.synthetic.main.fragment_release_list.*
import org.threeten.bp.LocalDate

class ReleaseListFragment : BaseFragment(R.layout.fragment_release_list, R.menu.main), DatePickerOpener {

    private val viewModel by viewModels<ReleaseListViewModel>()

    private val date: LocalDate? by lazy {
        arguments?.run { ReleaseListFragmentArgs.fromBundle(this).date?.asLocalDate }
    }

    private val listAdapter: ReleaseListAdapter by lazy { ReleaseListAdapter(requireContext()) }
    private lateinit var listLayoutManager: LinearLayoutManager
    private val listSpacing by lazy { context?.resources?.getDimension(SPACING_RES_DEFAULT)?.toInt() ?: 0 }
    private val listSkeleton by lazy { SkeletonFactory.createSkeleton(listView, R.layout.list_item_release_detail, 8) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.date -> { openDatePicker(); true }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        if (!isViewCreated) {
            setDate(date ?: Now.localDate())
        }
    }

    private fun initLayout() {
        val context = context ?: return

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

        listAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val isInitialLoad = itemCount > 0 && itemCount == listAdapter.itemCount
                if (isInitialLoad) {
                    scrollTo(date ?: Now.localDate())
                }
            }
        })
    }

    private fun setDate(date: LocalDate) {
        listSkeleton.showSkeleton()
        viewModel.observeReleases(date, this, ::setReleases)
    }

    private fun setReleases(list: PagedList<DateProvider>?) {
        listSkeleton.showOriginal()
        listAdapter.submitList(list)
    }

    private fun invalidateListHeader() {
        val firstVisibleListItemPosition = listLayoutManager.findFirstVisibleItemPosition()
        val firstVisibleDate = listAdapter.findFirstVisibleDateForPosition(firstVisibleListItemPosition)
        val currentDate = firstVisibleDate ?: return
        toolbar.title = currentDate.print(context)
    }

    private fun scrollTo(date: LocalDate) {
        if (isAdded) {
            val navigation = DateProviderNavigation(listAdapter)
            val position = navigation.getNearestPositionForDate(date) ?: return
            listView.stopScroll()
            // Skip header since date is being displayed in Toolbar as well
            listLayoutManager.scrollToPositionWithOffset(position + 1, listSpacing)
        }
    }

    private fun openDatePicker() {
        val firstVisibleListItemPosition = listLayoutManager.findFirstVisibleItemPosition()
        val firstVisibleDate = listAdapter.findFirstVisibleDateForPosition(firstVisibleListItemPosition)
        // FIXME: Date jumps back after selection
        openDatePicker(childFragmentManager, firstVisibleDate, onValueSelected = ::setDate)
    }
}