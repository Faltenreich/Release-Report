package com.faltenreich.release.domain.release.discover

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.base.date.Now
import com.faltenreich.release.base.date.print
import com.faltenreich.release.domain.date.DatePickerOpener
import com.faltenreich.release.domain.date.DateProvider
import com.faltenreich.release.domain.release.list.ReleaseDateItem
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.domain.release.search.SearchListAdapter
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.hideKeyboard
import com.faltenreich.release.framework.skeleton.SkeletonFactory
import kotlinx.android.synthetic.main.fragment_discover.*
import org.threeten.bp.LocalDate
import kotlin.math.abs
import kotlin.math.min

class DiscoverFragment : BaseFragment(R.layout.fragment_discover, R.menu.main), DatePickerOpener {

    private val viewModel by viewModels<DiscoverViewModel>()
    
    private lateinit var listAdapter: DiscoverListAdapter
    private lateinit var listLayoutManager: DiscoverLayoutManager
    private lateinit var listItemDecoration: DiscoverItemDecoration
    private val listSkeleton by lazy {
        SkeletonFactory.createSkeleton(listView, R.layout.list_item_release_image, 10)
    }

    private lateinit var searchListAdapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearch()
        initList()
        if (!isViewCreated) {
            setDate(Now.localDate())
        }
    }

    override fun onResume() {
        super.onResume()
        invalidateListHeader()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.date -> { openDatePicker(); true }
            else -> false
        }
    }

    private fun init() {
        listAdapter = DiscoverListAdapter(requireContext())
        searchListAdapter = SearchListAdapter(requireContext())
    }

    private fun initList() {
        val context = context ?: return

        listLayoutManager = DiscoverLayoutManager(context, listAdapter)
        listItemDecoration = DiscoverItemDecoration(context)

        listView.layoutManager = listLayoutManager
        listView.addItemDecoration(listItemDecoration)
        listView.adapter = listAdapter

        listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isAdded) {
                    invalidateListHeader()
                }
            }
        })
    }

    private fun initSearch() {
        val context = context ?: return

        searchResultListView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        searchResultListView.adapter = searchListAdapter

        searchView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) motionLayout.transitionToEnd()
            else motionLayout.transitionToStart()
        }
        searchView.doOnTextChanged { text, _, _, _ -> viewModel.query = text?.toString() }
        searchView.setOnClickListener { motionLayout.transitionToEnd() }
        searchButton.setOnClickListener {
            // TODO: Toggle depending on state
            motionLayout.transitionToStart()
        }

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {

            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) = Unit

            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) = Unit

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when (currentId) {
                    R.id.start -> {
                        searchButton.icon = ContextCompat.getDrawable(context, R.drawable.ic_search)
                        searchView.hideKeyboard()
                        view?.requestFocus()
                    }
                    R.id.end -> {
                        searchButton.icon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back)
                    }
                }
            }

            override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) = Unit
        })

        clearButton.setOnClickListener { searchView.text = null }
    }

    private fun setDate(date: LocalDate) {
        listSkeleton.showSkeleton()
        listItemDecoration.isSkeleton = true
        viewModel.observeReleases(date, this, ::setReleases)
        viewModel.observeQuery(this, ::setSearchResults)
    }

    private fun setReleases(list: PagedList<DateProvider>?) {
        listSkeleton.showOriginal()
        listItemDecoration.isSkeleton = false
        listAdapter.submitList(list)
    }

    private fun setSearchResults(list: PagedList<ReleaseProvider>?) {
        searchListAdapter.submitList(list)
    }

    private fun invalidateListHeader() {
        val firstVisibleItemPosition = listLayoutManager.findFirstVisibleItemPosition()
        val firstVisibleDate = listAdapter.findFirstVisibleDateForPosition(firstVisibleItemPosition)
        firstVisibleDate?.let { currentDate ->
            headerTextView.text = currentDate.print(context)
            headerTextView.isVisible = true
        } ?: run {
            headerTextView.isVisible = false
        }

        val firstCompletelyVisibleListItemPosition = listLayoutManager.findFirstCompletelyVisibleItemPosition()
        val secondVisibleListItem = listAdapter.currentList?.getOrNull(firstCompletelyVisibleListItemPosition)
        val approachingHeader = secondVisibleListItem is ReleaseDateItem

        headerTextView.translationY = if (approachingHeader) {
            val headerIndexInLayoutManager = firstCompletelyVisibleListItemPosition - firstVisibleItemPosition
            val headerHeight = headerTextView.height
            val secondVisibleListItemTop = listLayoutManager.getChildAt(headerIndexInLayoutManager)?.top ?: headerHeight
            val translateHeader = secondVisibleListItemTop != 0 && abs(secondVisibleListItemTop) < headerHeight
            if (translateHeader) {
                val top = secondVisibleListItemTop - headerTextView.height
                val translationY = min(top, 0)
                translationY.toFloat()
            } else {
                0f
            }
        } else {
            0f
        }
    }

    private fun openDatePicker() {
        val firstVisibleItemPosition = listLayoutManager.findFirstVisibleItemPosition()
        val firstVisibleDate = listAdapter.findFirstVisibleDateForPosition(firstVisibleItemPosition)
        openDatePicker(childFragmentManager, firstVisibleDate, onValueSelected = ::setDate)
    }
}