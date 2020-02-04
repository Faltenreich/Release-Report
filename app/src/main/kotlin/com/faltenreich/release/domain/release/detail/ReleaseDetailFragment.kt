package com.faltenreich.release.domain.release.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
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
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.fragment.invalidateOptionsMenu
import com.faltenreich.release.framework.android.tablayout.setupWithViewPager2
import com.faltenreich.release.framework.android.view.fitSystemWindows
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_release_detail.*
import kotlin.math.abs

class ReleaseDetailFragment : BaseFragment(
    R.layout.fragment_release_detail,
    R.menu.release
), DateOpener, UrlOpener, WebSearchOpener, ReleaseImageOpener {

    private val viewModel by lazy { createViewModel(ReleaseDetailViewModel::class) }
    private val infoViewModel by lazy { createSharedViewModel(ReleaseInfoViewModel::class) }
    private val imageListViewModel by lazy { createSharedViewModel(ImageListViewModel::class) }
    private val videoListViewModel by lazy { createSharedViewModel(VideoListViewModel::class) }

    private val releaseId by lazy {
        ReleaseDetailFragmentArgs.fromBundle(requireArguments()).releaseId
    }

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
        imageListViewModel.imageUrls = null
        videoListViewModel.videoUrls = null
    }

    private fun initLayout() {
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
            val url = release?.videoUrls?.firstOrNull() ?: release?.imageUrlForWallpaper
            openImage(findNavController(), release, url)
        }

        viewPager.adapter = ReleaseDetailFragmentAdapter(this)
        tabLayout.setupWithViewPager2(viewPager)
    }

    private fun fetchData() {
        viewModel.observeRelease(releaseId, this, ::setRelease)
    }

    private fun invalidateMetadata() {
        val release = viewModel.release
        collapsingToolbarLayout.title = release?.title
        wallpaperImageView.setWallpaper(release)
        videoIndicatorView.isVisible = release?.videoUrls?.firstOrNull() != null
    }

    private fun invalidateTint() {
        val (color, colorDark) = viewModel.color to viewModel.colorDark
        layoutContainer.setBackgroundResource(colorDark)
        collapsingToolbarLayout.setContentScrimResource(color)
        collapsingToolbarLayout.setStatusBarScrimResource(color)
    }

    private fun invalidateSubscription() {
        val isSubscribed = viewModel.release?.isSubscribed ?: false
        mainViewModel.fabConfig = FabConfig(
            iconRes = if (isSubscribed) R.drawable.ic_subscription_on else R.drawable.ic_subscription_off,
            backgroundColorRes = if (isSubscribed) R.color.yellow else android.R.color.white,
            foregroundColorRes = if (isSubscribed) R.color.brown else R.color.colorPrimaryDark,
            onClick = ::toggleSubscription
        )
    }

    private fun setRelease(release: Release?) {
        invalidateMetadata()
        invalidateTint()
        invalidateSubscription()
        invalidateOptionsMenu()

        infoViewModel.release = release
        imageListViewModel.imageUrls = release?.imageUrlsFull
        videoListViewModel.videoUrls = release?.videoUrls
    }

    private fun toggleSubscription() {
        val isSubscribed = !(viewModel.release?.isSubscribed.isTrue)
        viewModel.release?.isSubscribed = isSubscribed
        // FIXME: Replace with Snackbar when being placed behind BottomAppBar
        val message = getString(
            if (isSubscribed) R.string.subscription_added
            else R.string.subscription_removed
        )
        mainViewModel.showMessage(message)
        invalidateSubscription()
    }

    private fun openUrl(url: String?) {
        val context = context ?: return
        if (url == null) return
        openUrl(context, url)
    }
}