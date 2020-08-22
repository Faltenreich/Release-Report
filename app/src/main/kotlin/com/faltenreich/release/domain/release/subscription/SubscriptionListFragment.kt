package com.faltenreich.release.domain.release.subscription

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.base.date.Now
import com.faltenreich.release.base.date.asLocalDate
import com.faltenreich.release.domain.date.DatePickerOpener
import com.faltenreich.release.domain.date.DateProvider
import com.faltenreich.release.domain.release.list.ReleaseListAdapter
import com.faltenreich.release.domain.release.list.ReleaseListFragmentArgs
import com.faltenreich.release.domain.release.list.ReleaseListItemDecoration
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.recyclerview.decoration.ItemDecoration.Companion.SPACING_RES_DEFAULT
import com.faltenreich.release.framework.skeleton.SkeletonFactory
import kotlinx.android.synthetic.main.fragment_release_list.listView
import kotlinx.android.synthetic.main.fragment_release_list.toolbar
import kotlinx.android.synthetic.main.fragment_subscription_list.*
import org.threeten.bp.LocalDate

class SubscriptionListFragment : BaseFragment(R.layout.fragment_subscription_list, R.menu.main), DatePickerOpener {

    private val viewModel by viewModels<SubscriptionListViewModel>()

    private val date: LocalDate? by lazy {
        arguments?.run { ReleaseListFragmentArgs.fromBundle(this).date?.asLocalDate }
    }

    private val listAdapter: ReleaseListAdapter? by lazy { context?.let { context -> ReleaseListAdapter(context) } }
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
            initData(date ?: Now.localDate())
        }
    }

    private fun initLayout() {
        val context = context ?: return

        toolbar.setNavigationOnClickListener { finish() }

        listLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        listView.layoutManager = listLayoutManager
        listView.adapter = listAdapter
        listView.addItemDecoration(ReleaseListItemDecoration(context, listSpacing))

        listAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val totalItemCount = listAdapter?.itemCount ?: 0
                val isInitialLoad = itemCount > 0 && itemCount == totalItemCount
                if (isInitialLoad) {
                    scrollTo(date ?: Now.localDate())
                }
            }
        })
    }

    private fun initData(date: LocalDate) {
        listSkeleton.showSkeleton()
        viewModel.observeReleases(date, this, ::setReleases)
        viewModel.observeSubscriptionCount(this, ::setSubscriptionCount)
    }

    private fun setReleases(list: PagedList<DateProvider>?) {
        listSkeleton.showOriginal()
        listAdapter?.submitList(list)
    }

    private fun setSubscriptionCount(subscriptionCount: Int) {
        emptyView.isVisible = subscriptionCount == 0
    }

    private fun scrollTo(date: LocalDate) {
        if (isAdded) {
            val position = listAdapter?.getFirstPositionForDate(date)
                ?: listAdapter?.getNearestPositionForDate(date)
                ?: return
            listView.stopScroll()
            listLayoutManager.scrollToPositionWithOffset(position, listSpacing)
        }
    }

    private fun openDatePicker() {
        openDatePicker(childFragmentManager) { date -> initData(date) }
    }
}