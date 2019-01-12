package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.viewmodel.SpotlightViewModel
import com.faltenreich.releaseradar.ui.list.adapter.SpotlightListAdapter
import com.faltenreich.releaseradar.ui.list.decoration.HorizontalPaddingDecoration
import kotlinx.android.synthetic.main.fragment_spotlight.*

class SpotlightFragment : BaseFragment(R.layout.fragment_spotlight) {
    private val viewModel by lazy { createViewModel(SpotlightViewModel::class) }
    private val type by lazy { arguments?.getSerializable(ARGUMENT_MEDIA_TYPE) as? MediaType }

    private val weekListAdapter by lazy { context?.let { context -> SpotlightListAdapter(context) } }
    private val favoriteListAdapter by lazy { context?.let { context -> SpotlightListAdapter(context) } }

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

            favoriteListView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            favoriteListView.addItemDecoration(HorizontalPaddingDecoration(context, R.dimen.margin_padding_size_small))
            favoriteListView.adapter = favoriteListAdapter
        }
    }

    private fun fetchData() {
        type?.also { type ->
            weekListAdapter?.let { adapter ->
                viewModel.observeReleasesOfWeek(type, this) { releases ->
                    adapter.removeListItems()
                    adapter.addListItems(releases)
                    adapter.notifyDataSetChanged()
                }
            }
            favoriteListAdapter?.let { adapter ->
                viewModel.observeFollowing(type, this) { releases ->
                    adapter.removeListItems()
                    adapter.addListItems(releases)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        private const val ARGUMENT_MEDIA_TYPE = "mediaType"

        fun newInstance(type: MediaType): SpotlightFragment {
            val fragment = SpotlightFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARGUMENT_MEDIA_TYPE, type)
            fragment.arguments = arguments
            return fragment
        }
    }
}