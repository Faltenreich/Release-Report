package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.data.viewmodel.SpotlightViewModel
import com.faltenreich.release.ui.list.adapter.SpotlightListAdapter
import com.faltenreich.release.ui.list.item.SpotlightHeaderItem
import com.faltenreich.release.ui.list.item.SpotlightItem
import com.faltenreich.release.ui.list.item.SpotlightPromoItem
import com.faltenreich.release.ui.view.ReleaseOpener
import kotlinx.android.synthetic.main.fragment_spotlight.*

class SpotlightFragment : BaseFragment(R.layout.fragment_spotlight), ReleaseOpener {
    private val viewModel by lazy { createViewModel(SpotlightViewModel::class) }

    private val listAdapter by lazy { context?.let { context -> SpotlightListAdapter(context) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun initLayout() {
        context?.let { context ->
            listView.layoutManager = GridLayoutManager(context, COLUMN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (listAdapter?.getListItemAt(position)) {
                            is SpotlightHeaderItem -> COLUMN_COUNT
                            is SpotlightPromoItem -> COLUMN_COUNT
                            else -> 1
                        }
                    }
                }
            }
            // listView.addItemDecoration(GridSpacingItemDecoration(context, 2, R.dimen.margin_padding_size_small, includeEdge = false))
            listView.adapter = listAdapter
        }
    }

    private fun fetchData() {
        viewModel.observeData(this, MAX_ITEMS_PER_CATEGORY) { data -> setData(data) }
    }

    private fun setData(data: List<SpotlightItem>) {
        listAdapter?.let { adapter ->
            adapter.removeListItems()
            adapter.addListItems(data)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val COLUMN_COUNT = 3
        private const val MAX_ITEMS_PER_CATEGORY = COLUMN_COUNT * 2
    }
}