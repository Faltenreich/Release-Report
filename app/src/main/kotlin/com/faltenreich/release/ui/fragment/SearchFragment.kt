package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.ReleaseSearchViewModel
import com.faltenreich.release.extension.nonBlank
import com.faltenreich.release.extension.hideKeyboard
import com.faltenreich.release.ui.list.adapter.SearchListAdapter
import com.faltenreich.skeletonlayout.applySkeleton
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.view_empty.*
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.textResource

class SearchFragment : BaseFragment(R.layout.fragment_search), Search.OnQueryTextListener {
    private val viewModel by lazy { createViewModel(ReleaseSearchViewModel::class) }
    private val query: String? by lazy { arguments?.let { arguments -> SearchFragmentArgs.fromBundle(arguments).query.nonBlank } }

    private val listAdapter by lazy { context?.let { context -> SearchListAdapter(context) } }
    private lateinit var listLayoutManager: LinearLayoutManager

    private val skeleton by lazy { listView.applySkeleton(R.layout.list_item_release_detail, itemCount = 6) }

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
            if (query != null) {
                skeleton.showSkeleton()
                emptyView.isVisible = false
                searchView.setQuery(query, true)
            } else {
                emptyView.isVisible = true
                emptyIcon.isVisible = false
                emptyLabel.textResource = R.string.search_hint_desc
                searchView.open(null)
            }
        }
    }

    private fun initData() {
        viewModel.observe(this, onObserve = { releases ->
            listAdapter?.submitList(releases)
        }, onInitialLoad = { releases ->
            runOnUiThread {
                skeleton.showOriginal()
                emptyView.isVisible = releases.isEmpty()
                emptyIcon.isVisible = true
                emptyLabel.textResource = R.string.nothing_found
            }
        })
    }

    override fun onQueryTextChange(newText: CharSequence?) = Unit

    override fun onQueryTextSubmit(query: CharSequence?): Boolean {
        viewModel.query = query?.toString().nonBlank
        hideKeyboard()
        return true
    }
}