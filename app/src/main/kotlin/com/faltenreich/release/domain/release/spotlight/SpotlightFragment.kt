package com.faltenreich.release.domain.release.spotlight

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.domain.release.search.SearchOpener
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_spotlight.*
import kotlinx.android.synthetic.main.skeleton_spotlight.*
import kotlinx.android.synthetic.main.view_empty.*

class SpotlightFragment : BaseFragment(
    R.layout.fragment_spotlight,
    R.menu.main
), ReleaseOpener, SearchOpener {

    private val viewModel by lazy { createViewModel(SpotlightViewModel::class) }

    private lateinit var listAdapter: SpotlightListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.date).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> { openSearch(findNavController()); true }
            else -> false
        }
    }

    private fun init() {
        listAdapter = SpotlightListAdapter(requireContext())
    }

    private fun initLayout() {
        listView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listView.addItemDecoration(SpotlightItemDecoration(requireContext()))
        listView.adapter = listAdapter
    }

    private fun fetchData() {
        skeletonLayout.isVisible = true
        skeletonLayout.showSkeleton()
        viewModel.observeData(this) { data ->
            skeletonLayout.showOriginal()
            skeletonLayout.isVisible = false
            setData(data)
        }
    }

    private fun setData(data: List<SpotlightItem>) {
        listAdapter.apply {
            removeListItems()
            addListItems(data)
            notifyDataSetChanged()
        }
        emptyView.isVisible = data.isEmpty()
    }
}