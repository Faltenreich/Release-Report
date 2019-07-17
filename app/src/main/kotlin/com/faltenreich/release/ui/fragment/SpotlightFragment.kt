package com.faltenreich.release.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.viewmodel.SpotlightViewModel
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.list.adapter.SpotlightListAdapter
import com.faltenreich.release.ui.list.decoration.HorizontalPaddingDecoration
import com.faltenreich.release.ui.view.ReleaseOpener
import kotlinx.android.synthetic.main.fragment_spotlight.*

class SpotlightFragment : BaseFragment(R.layout.fragment_spotlight), ReleaseOpener {
    private val viewModel by lazy { createViewModel(SpotlightViewModel::class) }

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

            weekListView.layoutManager = GridLayoutManager(context, 2)
            weekListView.addItemDecoration(decoration)
            weekListView.adapter = weekListAdapter

            favoriteListView.layoutManager = GridLayoutManager(context, 2)
            favoriteListView.addItemDecoration(decoration)
            favoriteListView.adapter = favoriteListAdapter

            recentListView.layoutManager = GridLayoutManager(context, 2)
            recentListView.addItemDecoration(decoration)
            recentListView.adapter = recentListAdapter
        }
    }

    private fun fetchData() {
        viewModel.observeWeeklyReleases(this) { releases ->
            setSpotlightRelease(releases.firstOrNull())
            setReleasesOfWeek(releases.drop(1).sortedBy(Release::releaseDate))
        }
        viewModel.observeFavoriteReleases(this) { releases -> setFavoriteReleases(releases) }
        viewModel.observeRecentReleases(this) { releases -> setRecentReleases(releases.sortedByDescending(Release::releaseDate)) }
    }

    private fun setSpotlightRelease(release: Release?) {
        context?.let { context ->
            val hasContent = release != null
            spotlightReleaseCoverImageView.visibility = if (hasContent) View.VISIBLE else View.GONE
            spotlightReleaseNameTextView.visibility = if (hasContent) View.VISIBLE else View.GONE

            spotlightReleaseCoverImageView.setOnClickListener { release?.let { openRelease(context, it, spotlightReleaseCoverImageView) } }
            release?.imageUrlForWallpaper?.let { imageUrl -> spotlightReleaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: spotlightReleaseCoverImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
            spotlightReleaseNameTextView.text = release?.title
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
}