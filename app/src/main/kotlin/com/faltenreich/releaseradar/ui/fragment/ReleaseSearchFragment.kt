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
        // FIXME: Workaround to reset shadow after onPause
        searchView.setShadow(true)
    }

    override fun onPause() {
        super.onPause()
        // FIXME: Workaround to prevent visible shadow onResume
        searchView.setShadow(false)
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

        searchView.setOnLogoClickListener { toolbarDelegate?.onHamburgerIconClicked() }
        searchView.doOnPreDraw {
            // FIXME: Workaround to reset shadow after onRestoreInstanceState
            searchView.setShadow(true)
        }
        searchView.setOnQueryTextListener(object : Search.OnQueryTextListener {
            override fun onQueryTextChange(newText: CharSequence?) = Unit
            override fun onQueryTextSubmit(query: CharSequence?): Boolean {
                viewModel.query = query?.toString()?.nonBlank
                return true
            }
        })
    }

    private fun initData() {
        viewModel.observe(this) { releases -> listAdapter?.submitList(releases) }
        searchView.setQuery(query, true)
    }
}