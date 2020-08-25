package com.faltenreich.release.domain.release.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.faltenreich.release.R
import com.faltenreich.release.base.intent.UrlOpener
import com.faltenreich.release.base.intent.WebSearchOpener
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.date.DateOpener
import com.faltenreich.release.domain.media.image.ImageListViewModel
import com.faltenreich.release.domain.media.video.VideoListViewModel
import com.faltenreich.release.domain.navigation.FabConfig
import com.faltenreich.release.domain.release.ReleaseImageOpener
import com.faltenreich.release.domain.release.setWallpaper
import com.faltenreich.release.framework.android.context.getColorFromAttribute
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.view.tablayout.setupWithViewPager2
import com.faltenreich.release.framework.android.view.toolbar.fitSystemWindows
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_release_detail.*
import kotlin.math.abs

class ReleaseDetailFragment : BaseFragment(
    R.layout.fragment_release_detail,
    R.menu.release
), DateOpener, UrlOpener, WebSearchOpener, ReleaseImageOpener {

    private val viewModel by viewModels<ReleaseDetailViewModel>()
    private val infoViewModel by activityViewModels<ReleaseInfoViewModel>()
    private val imageListViewModel by activityViewModels<ImageListViewModel>()
    private val videoListViewModel by activityViewModels<VideoListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    override fun onResume() {
        super.onResume()
        invalidateSubscription()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.web_search -> { searchInWeb(context, viewModel.release?.titleFull); true }
            R.id.web_open -> { openUrl(viewModel.release?.externalUrl); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.web_open).isVisible = viewModel.release?.externalUrl != null
    }

    override fun onPause() {
        mainViewModel.fabConfig = null
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        infoViewModel.release = null
        infoViewModel.genres = null
        infoViewModel.platforms = null
        imageListViewModel.imageUrls = null
        videoListViewModel.videoUrls = null
    }

    private fun initLayout() {
        val context = context ?: return

        toolbar.setNavigationOnClickListener { finish() }
        toolbar.fitSystemWindows()

        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appbarLayout, verticalOffset ->
            val currentOffset = abs(verticalOffset.toFloat())
            val maxOffset = appbarLayout.height.toFloat() - toolbar.height
            val scale = (maxOffset - currentOffset) / maxOffset
            videoIndicatorView.scaleX = scale
            videoIndicatorView.scaleY = scale
        })

        wallpaperImageView.setOnClickListener {
            val release = viewModel.release
            val imageUrl = release?.videoUrls?.firstOrNull() ?: release?.imageUrlForWallpaper
            imageUrl ?: return@setOnClickListener
            openImage(findNavController(), release, imageUrl)
        }

        viewPager.adapter = ReleaseDetailFragmentAdapter(this)
        tabLayout.setupWithViewPager2(viewPager)
    }

    private fun fetchData() {
        val releaseAsJson = ReleaseDetailFragmentArgs.fromBundle(requireArguments()).releaseAsJson
        viewModel.observeRelease(releaseAsJson, this, ::setRelease)
    }

    private fun invalidateMetadata() {
        val release = viewModel.release
        collapsingToolbarLayout.title = release?.title
        wallpaperImageView.setWallpaper(release)
        videoIndicatorView.isVisible = release?.videoUrls?.firstOrNull() != null
    }

    private fun invalidateSubscription() {
        val context = context ?: return
        val isSubscribed = viewModel.release?.isSubscribed ?: false

        val icon = ContextCompat.getDrawable(context,
            if (isSubscribed) R.drawable.ic_subscription_on
            else R.drawable.ic_subscription_off
        ) ?: return
        val backgroundColor =
            if (isSubscribed) ContextCompat.getColor(context, R.color.yellow)
            else context.getColorFromAttribute(R.attr.backgroundColorSecondary)
        val foregroundColor =
            if (isSubscribed) ContextCompat.getColor(context, R.color.brown)
            else context.getColorFromAttribute(android.R.attr.textColorPrimary)

        mainViewModel.fabConfig = FabConfig(icon, backgroundColor, foregroundColor, ::toggleSubscription)
    }

    private fun setRelease(release: Release?) {
        invalidateMetadata()
        invalidateSubscription()
        activity?.invalidateOptionsMenu()

        infoViewModel.release = release
        imageListViewModel.imageUrls = release?.imageUrlsFull
        videoListViewModel.videoUrls = release?.videoUrls
    }

    private fun toggleSubscription() {
        val isSubscribed = !(viewModel.release?.isSubscribed.isTrue)
        viewModel.release?.isSubscribed = isSubscribed
        invalidateSubscription()
    }

    private fun openUrl(url: String?) {
        val context = context ?: return
        if (url == null) return
        openUrl(context, url)
    }
}