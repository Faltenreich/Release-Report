package com.faltenreich.release.domain.release.subscription

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.base.date.Now
import com.faltenreich.release.domain.release.DateProviderNavigation
import com.faltenreich.release.domain.release.list.ReleaseListItemDecoration
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.recyclerview.decoration.ItemDecoration.Companion.SPACING_RES_DEFAULT
import com.faltenreich.release.framework.skeleton.SkeletonFactory
import kotlinx.android.synthetic.main.fragment_release_list.listView
import kotlinx.android.synthetic.main.fragment_release_list.toolbar
import kotlinx.android.synthetic.main.fragment_subscription_list.*
import org.threeten.bp.LocalDate

class SubscriptionListFragment : BaseFragment(R.layout.fragment_subscription_list) {

    private val viewModel by viewModels<SubscriptionListViewModel>()

    private lateinit var listAdapter: SubscriptionListAdapter
    private lateinit var listLayoutManager: LinearLayoutManager
    private val listSpacing by lazy { requireContext().resources?.getDimension(SPACING_RES_DEFAULT)?.toInt() ?: 0 }
    private val listSkeleton by lazy { SkeletonFactory.createSkeleton(listView, R.layout.list_item_release_detail, 8) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        if (!isViewCreated) {
            initData()
        }
    }

    private fun init() {
        listAdapter = SubscriptionListAdapter(requireContext())
    }

    private fun initLayout() {
        toolbar.setNavigationOnClickListener { finish() }

        listLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listView.layoutManager = listLayoutManager
        listView.adapter = listAdapter
        listView.addItemDecoration(ReleaseListItemDecoration(requireContext(), listSpacing))
    }

    private fun initData() {
        listSkeleton.showSkeleton()
        viewModel.observeReleases(this, ::setReleases)
    }

    private fun setReleases(list: List<ReleaseProvider>?) {
        listSkeleton.showOriginal()
        emptyView.isVisible = list.isNullOrEmpty()
        listAdapter.apply {
            removeListItems()
            addListItems(list ?: listOf())
            notifyDataSetChanged()
        }
        scrollTo(Now.localDate())
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
}