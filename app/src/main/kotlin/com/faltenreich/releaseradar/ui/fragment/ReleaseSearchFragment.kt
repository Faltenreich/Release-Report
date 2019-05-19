package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.ReleaseSearchViewModel
import com.faltenreich.releaseradar.extension.nonBlank
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseSearchListAdapter
import com.faltenreich.skeletonlayout.applySkeleton
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_release_search.*
import org.jetbrains.anko.support.v4.runOnUiThread

class ReleaseSearchFragment : BaseFragment(R.layout.fragment_release_search), Search.OnQueryTextListener {
    private val viewModel by lazy { createViewModel(ReleaseSearchViewModel::class) }
    private val query: String? by lazy { arguments?.let { arguments -> ReleaseSearchFragmentArgs.fromBundle(arguments).query } }

    private val listAdapter by lazy { context?.let { context -> ReleaseSearchListAdapter(context) } }
    private lateinit var listLayoutManager: LinearLayoutManager

    private val skeleton by lazy { listView.applySkeleton(R.layout.list_item_release_search, itemCount = 6) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    private fun initLayout() {
        listLayoutManager = LinearLayoutManager(context)
        listView.layoutManager = listLayoutManager
        listView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listView.adapter = listAdapter

        searchView.logo = Search.Logo.ARROW
        searchView.setOnLogoClickListener { finish() }
        searchView.setOnQueryTextListener(this)

        if (viewModel.query == null) {
            searchView.setQuery(query, true)
        }
    }

    private fun initData() {
        skeleton.showSkeleton()
        viewModel.observe(this, onObserve = { releases ->
            listAdapter?.submitList(releases)
        }, onInitialLoad = { releases ->
            runOnUiThread {
                skeleton.showOriginal()
                listViewContainer.isVisible = releases.isNotEmpty()
                listEmptyView.isVisible = releases.isEmpty()
            }
        })
    }

    override fun onQueryTextChange(newText: CharSequence?) = Unit

    override fun onQueryTextSubmit(query: CharSequence?): Boolean {
        viewModel.query = query?.toString().nonBlank
        return true
    }
}