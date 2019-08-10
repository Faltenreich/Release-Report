package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.SearchViewModel
import com.faltenreich.release.extension.hideKeyboard
import com.faltenreich.release.extension.nonBlank
import com.faltenreich.release.ui.list.adapter.SearchListAdapter
import com.faltenreich.release.ui.list.decoration.DividerItemDecoration
import com.faltenreich.release.ui.view.SkeletonFactory
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.view_empty.*

class SearchFragment : BaseFragment(R.layout.fragment_search), Search.OnQueryTextListener {
    private val viewModel by lazy { createViewModel(SearchViewModel::class) }
    private val query: String? by lazy { arguments?.let { arguments -> SearchFragmentArgs.fromBundle(arguments).query.nonBlank } }

    private val listAdapter by lazy { context?.let { context -> SearchListAdapter(context) } }
    private lateinit var listLayoutManager: LinearLayoutManager
    private val listSkeleton by lazy { SkeletonFactory.createSkeleton(listView, R.layout.list_item_release_detail, 6) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        if (!isViewCreated) {
            initData()
        }
    }

    private fun initLayout() {
        context?.let { context ->
            listLayoutManager = LinearLayoutManager(context)
            listView.layoutManager = listLayoutManager
            listView.addItemDecoration(DividerItemDecoration(context))
            listView.adapter = listAdapter

            searchView.logo = Search.Logo.ARROW
            searchView.setOnLogoClickListener { finish() }
            searchView.setOnQueryTextListener(this)
        }
    }

    private fun initData() {
        if (viewModel.query == null) {
            if (query != null) {
                searchView.setQuery(query, true)
            } else {
                searchView.open(null)
            }
        }

        viewModel.observe(this, onObserve = { list ->
            listSkeleton.showOriginal()
            listAdapter?.submitList(list)
        }, afterInitialLoad = { list ->
            emptyView.isVisible = list.isEmpty()
        })
    }

    override fun onQueryTextChange(newText: CharSequence?) = Unit

    override fun onQueryTextSubmit(query: CharSequence?): Boolean {
        hideKeyboard()
        listSkeleton.showSkeleton()
        viewModel.query = query?.toString().nonBlank
        return true
    }
}