package com.faltenreich.release.domain.release.spotlight

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_spotlight.*
import kotlinx.android.synthetic.main.skeleton_spotlight.*
import kotlinx.android.synthetic.main.view_empty.*

class SpotlightFragment : BaseFragment(R.layout.fragment_spotlight, R.menu.main), ReleaseOpener {

    private val viewModel by viewModels<SpotlightViewModel>()

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

    private fun init() {
        listAdapter = SpotlightListAdapter(requireContext())
    }

    private fun initLayout() {
        val context = context ?: return
        listView.layoutManager = LinearLayoutManager(context)
        listView.addItemDecoration(SpotlightItemDecoration(context))
        listView.adapter = listAdapter
    }

    private fun fetchData() {
        skeletonLayout.isVisible = true
        skeletonLayout.showSkeleton()
        viewModel.observeData(this, ::setData)
    }

    private fun setData(data: List<SpotlightItem>?) {
        val items = data ?: listOf()
        listAdapter.apply {
            removeListItems()
            addListItems(items)
            notifyDataSetChanged()
        }
        skeletonLayout.showOriginal()
        skeletonLayout.isVisible = false
        emptyView.isVisible = items.isEmpty()
    }
}