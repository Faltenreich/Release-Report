package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.viewmodel.SpotlightViewModel
import com.faltenreich.releaseradar.ui.list.adapter.SpotlightListAdapter
import com.faltenreich.releaseradar.ui.list.decoration.HorizontalPaddingDecoration
import kotlinx.android.synthetic.main.fragment_spotlight.*

class SpotlightFragment : BaseFragment(R.layout.fragment_spotlight) {
    private val viewModel by lazy { createViewModel(SpotlightViewModel::class) }

    private val weekListAdapter by lazy { context?.let { context -> SpotlightListAdapter(context) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun initLayout() {
        context?.let { context ->
            weekListView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            weekListView.addItemDecoration(HorizontalPaddingDecoration(context, R.dimen.margin_padding_size_small))
            weekListView.adapter = weekListAdapter
        }
    }

    private fun fetchData() {
        // TODO: Find way to distinguish back navigation via Navigation Components
        if (weekListAdapter?.itemCount == 0) {
            viewModel.observeReleasesOfWeek(this) { releases -> weekListAdapter?.submitList(releases) }
        }
    }
}