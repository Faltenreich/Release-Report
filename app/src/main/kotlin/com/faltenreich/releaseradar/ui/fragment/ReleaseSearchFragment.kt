package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.ReleaseSearchViewModel
import com.faltenreich.releaseradar.extension.nonBlank
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseSearchListAdapter
import com.faltenreich.skeletonlayout.applySkeleton
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_release_search.*

class ReleaseSearchFragment : BaseFragment(R.layout.fragment_release_search) {
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
        searchView.setOnQueryTextListener(object : Search.OnQueryTextListener {
            override fun onQueryTextChange(newText: CharSequence?) = Unit
            override fun onQueryTextSubmit(query: CharSequence?): Boolean {
                viewModel.query = query?.toString().nonBlank
                return false
            }
        })
        searchView.setQuery(query, true)
    }

    private fun initData() {
        viewModel.observe(this, onObserve = { releases ->
            skeleton.showSkeleton()
            listViewContainer.visibility = View.VISIBLE
            listEmptyView.visibility = View.GONE
            listAdapter?.submitList(releases)
        }, onInitialLoad = { releases ->
            skeleton.showOriginal()
            listViewContainer.visibility = if (releases.isNotEmpty()) View.VISIBLE else View.GONE
            listEmptyView.visibility = if (releases.isNotEmpty()) View.GONE else View.VISIBLE
        })
    }
}