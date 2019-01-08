package com.faltenreich.releaseradar.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.ReleaseSearchViewModel
import com.faltenreich.releaseradar.extension.nonBlank
import com.faltenreich.releaseradar.ui.adapter.ReleaseSearchListAdapter
import com.lapism.searchview.Search
import kotlinx.android.synthetic.main.fragment_release_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReleaseSearchFragment : BaseFragment(R.layout.fragment_release_search) {
    private val viewModel by lazy { createViewModel(ReleaseSearchViewModel::class) }
    private val query: String? by lazy { arguments?.let { arguments -> ReleaseSearchFragmentArgs.fromBundle(arguments).query } }

    private val listAdapter by lazy { context?.let { context -> ReleaseSearchListAdapter(context) } }
    private lateinit var listLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    override fun onResume() {
        super.onResume()
        invalidatePaddingForTranslucentStatusBar()
    }

    private fun invalidatePaddingForTranslucentStatusBar() {
        view?.doOnPreDraw {
            val frame = Rect()
            activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
            appbarLayout.setPadding(0, frame.top, 0, 0)
            searchView.setPadding(0, frame.top, 0, 0)
            statusBarBackground.layoutParams.height = frame.top
        }
    }

    private fun initLayout() {
        listLayoutManager = LinearLayoutManager(context)

        listView.layoutManager = listLayoutManager
        listView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listView.adapter = listAdapter

        searchView.logo = Search.Logo.ARROW
        searchView.setOnLogoClickListener { finish() }
        searchView.setOnQueryTextListener(object : Search.OnQueryTextListener {
            override fun onQueryTextChange(newText: CharSequence?) {
                val query = newText?.toString().nonBlank
                GlobalScope.launch {
                    delay(QUERY_DELAY)
                    if (searchView.query.toString().nonBlank == query) {
                        viewModel.query = query
                    }
                }
            }
            override fun onQueryTextSubmit(query: CharSequence?): Boolean = false
        })
    }

    private fun initData() {
        // TODO: Find way to distinguish back navigation via Navigation Components
        if (listAdapter?.itemCount == 0) {
            viewModel.observe(this) { releases -> listAdapter?.submitList(releases) }
            searchView.setQuery(query, false)
        }
    }

    companion object {
        private const val QUERY_DELAY = 500L
    }
}