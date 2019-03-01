package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.viewmodel.ReleaseFavoriteListViewModel
import com.faltenreich.releaseradar.ui.list.adapter.FollowingListAdapter
import com.faltenreich.releaseradar.ui.view.CalendarEvent
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : BaseFragment(R.layout.fragment_following) {
    private val viewModel by lazy { createViewModel(ReleaseFavoriteListViewModel::class) }

    private val listAdapter by lazy { context?.let { context -> FollowingListAdapter(context) } }
    private val skeleton by lazy { listView.applySkeleton(R.layout.list_item_release_search, itemCount = 6) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun initLayout() {
        context?.let { context ->
            listView.layoutManager = LinearLayoutManager(context)
            listView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            listView.adapter = listAdapter
        }
    }

    private fun fetchData() {
        skeleton.showSkeleton()
        viewModel.observeFavoriteReleases(this) { releases -> setReleases(releases) }
    }

    private fun setReleases(releases: List<Release>) {
        calendarView.apply {
            removeAllEvents()
            addEvents(releases.mapNotNull { release -> CalendarEvent.fromRelease(context, release) })
        }

        listAdapter?.let { adapter ->
            adapter.removeListItems()
            adapter.addListItems(releases)
            adapter.notifyDataSetChanged()
            skeleton.showOriginal()
        }
    }
}