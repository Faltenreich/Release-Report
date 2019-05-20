package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.viewmodel.ReleaseFavoriteListViewModel
import com.faltenreich.releaseradar.extension.printMonth
import com.faltenreich.releaseradar.ui.list.adapter.FollowingListAdapter
import com.faltenreich.releaseradar.ui.view.CalendarEvent
import com.faltenreich.skeletonlayout.applySkeleton
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.fragment_following.listEmptyView
import kotlinx.android.synthetic.main.fragment_following.listView
import org.threeten.bp.LocalDate
import java.util.*

class FollowingFragment : BaseFragment(R.layout.fragment_following) {
    private val viewModel by lazy { createViewModel(ReleaseFavoriteListViewModel::class) }

    private val listAdapter by lazy { context?.let { context -> FollowingListAdapter(context) } }
    private val skeleton by lazy { listView.applySkeleton(R.layout.list_item_release_search, itemCount = 6) }
    private lateinit var listLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun initLayout() {
        context?.let { context ->
            listLayoutManager = LinearLayoutManager(context)
            listView.layoutManager = listLayoutManager
            listView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            listView.adapter = listAdapter

            listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (isAdded) {
                        invalidateCalendarView()
                    }
                }
            })

            calendarView.setListener(object : CompactCalendarView.CompactCalendarViewListener {
                override fun onDayClick(dateClicked: Date?) = Unit
                override fun onMonthScroll(firstDayOfNewMonth: Date?) = invalidateList()
            })
        }
    }

    private fun fetchData() {
        skeleton.showSkeleton()
        viewModel.observeFavoriteReleases(this) { releases -> setReleases(releases) }
    }

    private fun setReleases(releases: List<Release>) {
        val hasContent = releases.isNotEmpty()
        listEmptyView.isVisible = !hasContent

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

    private fun invalidateCalendarView() {
        val firstVisibleListItemPosition = listLayoutManager.findFirstVisibleItemPosition()
        val date = listAdapter?.getListItemAt(firstVisibleListItemPosition)?.releaseDate ?: LocalDate.now()
        calendarView.date = date
        invalidateDateLabel()
    }

    private fun invalidateList() {
        // TODO: Scroll to date
        invalidateDateLabel()
    }

    private fun invalidateDateLabel() {
        dateLabel.text = calendarView.date.printMonth(context)
    }
}