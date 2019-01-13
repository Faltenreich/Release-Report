package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.ReleaseFavoriteListViewModel
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseFavoriteListAdapter
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.android.synthetic.main.fragment_release_favorite.*

class ReleaseFavoriteListFragment : BaseFragment(R.layout.fragment_release_favorite) {
    private val viewModel by lazy { createViewModel(ReleaseFavoriteListViewModel::class) }

    private val listAdapter by lazy { context?.let { context -> ReleaseFavoriteListAdapter(context) } }
    private val skeleton by lazy { listView.applySkeleton(R.layout.list_item_release_search, itemCount = 6) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun initLayout() {
        context?.let { context ->
            (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
            toolbar.setupWithNavController(findNavController())

            listView.layoutManager = LinearLayoutManager(context)
            listView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            listView.adapter = listAdapter
        }
    }

    private fun fetchData() {
        skeleton.showSkeleton()
        viewModel.observeFavoriteReleases(this) { releases ->
            listAdapter?.let { adapter ->
                adapter.removeListItems()
                adapter.addListItems(releases)
                adapter.notifyDataSetChanged()
                skeleton.showOriginal()
            }
        }
    }
}