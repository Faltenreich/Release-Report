package com.faltenreich.releaseradar.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.enum.MediaType
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.viewmodel.SpotlightViewModel
import com.faltenreich.releaseradar.extension.screenSize
import com.faltenreich.releaseradar.extension.setImageAsync
import com.faltenreich.releaseradar.ui.list.adapter.SpotlightListAdapter
import com.faltenreich.releaseradar.ui.list.decoration.HorizontalPaddingDecoration
import com.faltenreich.releaseradar.ui.view.ReleaseOpener
import kotlinx.android.synthetic.main.fragment_spotlight.*

class SpotlightFragment : BaseFragment(R.layout.fragment_spotlight), ReleaseOpener {
    private val viewModel by lazy { createViewModel(SpotlightViewModel::class) }
    private val type by lazy { arguments?.getSerializable(ARGUMENT_MEDIA_TYPE) as? MediaType }

    private val weekListAdapter by lazy { context?.let { context -> SpotlightListAdapter(context) } }
    private val favoriteListAdapter by lazy { context?.let { context -> SpotlightListAdapter(context) } }
    private val recentListAdapter by lazy { context?.let { context -> SpotlightListAdapter(context) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun initLayout() {
        context?.let { context ->
            val decoration = HorizontalPaddingDecoration(context, R.dimen.margin_padding_size_small)

            weekListView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            weekListView.addItemDecoration(decoration)
            weekListView.adapter = weekListAdapter

            favoriteListView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            favoriteListView.addItemDecoration(decoration)
            favoriteListView.adapter = favoriteListAdapter

            recentListView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            recentListView.addItemDecoration(decoration)
            recentListView.adapter = recentListAdapter
        }
    }

    private fun fetchData() {
        type?.also { type ->
            viewModel.observeWeeklyReleases(type, this) { releases ->
                setSpotlightRelease(releases.firstOrNull())
                setReleasesOfWeek(releases.drop(1).sortedBy(Release::releaseDate))
            }
            viewModel.observeFavoriteReleases(type, this) { releases -> setFavoriteReleases(releases) }
            viewModel.observeRecentReleases(type, this) { releases -> setRecentReleases(releases.sortedByDescending(Release::releaseDate)) }
        }
    }

    private fun setSpotlightRelease(release: Release?) {
        context?.let { context ->
            val hasContent = release != null
            spotlightContainer.visibility = if (hasContent) View.VISIBLE else View.GONE

            release?.imageUrlForThumbnail?.let { imageUrl -> spotlightReleaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: spotlightReleaseCoverImageView.setImageResource(android.R.color.transparent)
            spotlightReleaseDateTextView.text = release?.releaseDateForUi(context)
            spotlightReleaseNameTextView.text = release?.title
            spotlightReleaseDescriptionTextView.text = release?.description
            spotlightContainer.setOnClickListener { release?.let { openRelease(context, it, spotlightReleaseCoverImageView) } }
        }
    }

    private fun setReleasesOfWeek(releases: List<Release>) {
        val hasContent = releases.isNotEmpty()
        weekListView.isInvisible = !hasContent
        weekEmptyView.isVisible = !hasContent
        weekListAdapter?.let { adapter ->
            adapter.removeListItems()
            adapter.addListItems(releases)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setFavoriteReleases(releases: List<Release>) {
        val hasContent = releases.isNotEmpty()
        favoriteLabel.isVisible = hasContent
        favoriteListView.isVisible = hasContent
        favoriteListAdapter?.let { adapter ->
            adapter.removeListItems()
            adapter.addListItems(releases)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setRecentReleases(releases: List<Release>) {
        val hasContent = releases.isNotEmpty()
        recentListView.isInvisible = !hasContent
        recentEmptyView.isVisible = !hasContent
        recentListAdapter?.let { adapter ->
            adapter.removeListItems()
            adapter.addListItems(releases)
            adapter.notifyDataSetChanged()
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