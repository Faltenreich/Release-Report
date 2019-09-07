package com.faltenreich.release.domain.release.discover

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.base.date.print
import com.faltenreich.release.base.primitive.nonBlank
import com.faltenreich.release.domain.date.DatePickerOpener
import com.faltenreich.release.domain.release.list.ReleaseDateItem
import com.faltenreich.release.domain.release.search.SearchOpener
import com.faltenreich.release.domain.release.search.SearchableObserver
import com.faltenreich.release.domain.release.search.SearchableProperties
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.skeleton.SkeletonFactory
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_discover.*
import org.threeten.bp.LocalDate
import kotlin.math.abs
import kotlin.math.min

class DiscoverFragment : BaseFragment(
    R.layout.fragment_discover,
    R.menu.main
), DatePickerOpener, SearchOpener {

    private val viewModel by lazy { createViewModel(DiscoverViewModel::class) }
    private val searchable by lazy { SearchableObserver() }
    
    private val listAdapter by lazy { context?.let { context -> DiscoverListAdapter(context) } }
    private lateinit var listLayoutManager: DiscoverLayoutManager
    private lateinit var listItemDecoration: DiscoverItemDecoration
    private val listSkeleton by lazy { SkeletonFactory.createSkeleton(listView, R.layout.list_item_release_image, 10) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(searchable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearch()
        initList()
        if (!isViewCreated) {
            initData(LocalDate.now())
        }
    }

    override fun onResume() {
        super.onResume()
        invalidateListHeader()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.date -> { openDatePicker(childFragmentManager) { date -> initData(date) }; true }
            R.id.search -> { openSearch(findNavController(), searchView.query.toString()); true }
            else -> false
        }
    }

    private fun initSearch() {
        searchable.properties = SearchableProperties(this, searchView)
        searchView.setLogoIcon(R.drawable.ic_search)
        searchView.setOnLogoClickListener {  }
        searchView.setOnQueryTextListener(object : Search.OnQueryTextListener {
            override fun onQueryTextChange(newText: CharSequence?) = Unit
            override fun onQueryTextSubmit(query: CharSequence?): Boolean {
                val searchQuery = query?.toString()?.nonBlank ?: return true
                openSearch(findNavController(), searchQuery)
                return true
            }
        })
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

    private fun initData(date: LocalDate) {
        listSkeleton.showSkeleton()
        listItemDecoration.isSkeleton = true

        viewModel.observeReleases(date, this) { list ->
            listSkeleton.showOriginal()
            listItemDecoration.isSkeleton = false
            listAdapter?.submitList(list)
        }
    }

    private fun invalidateListHeader() {
        val firstVisibleListItemPosition = listLayoutManager.findFirstVisibleItemPosition()
        val firstVisibleListItem = listAdapter?.currentList?.getOrNull(firstVisibleListItemPosition)
        val currentDate = firstVisibleListItem?.date ?: LocalDate.now()
        headerTextView.doOnPreDraw { headerTextView.text = currentDate?.print(context) }

        val firstCompletelyVisibleListItemPosition = listLayoutManager.findFirstCompletelyVisibleItemPosition()
        val secondVisibleListItem = listAdapter?.currentList?.getOrNull(firstCompletelyVisibleListItemPosition)
        val approachingHeader = secondVisibleListItem is ReleaseDateItem

        headerTextView.translationY = if (approachingHeader) {
            val headerIndexInLayoutManager = firstCompletelyVisibleListItemPosition - firstVisibleListItemPosition
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
}