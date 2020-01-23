package com.faltenreich.release.domain.release.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.base.intent.UrlOpener
import com.faltenreich.release.base.intent.WebSearchOpener
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.date.DateOpener
import com.faltenreich.release.domain.media.ImageListFragment
import com.faltenreich.release.domain.media.ImageUrlObserver
import com.faltenreich.release.domain.release.ReleaseObserver
import com.faltenreich.release.domain.release.setWallpaper
import com.faltenreich.release.framework.android.context.showToast
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.fragment.invalidateOptionsMenu
import com.faltenreich.release.framework.android.tablayout.setupWithViewPager2
import com.faltenreich.release.framework.android.view.backgroundTintResource
import com.faltenreich.release.framework.android.view.fitSystemWindows
import com.faltenreich.release.framework.android.view.tintResource
import com.faltenreich.release.framework.android.viewpager.ViewPager2FragmentAdapter
import kotlinx.android.synthetic.main.fragment_release_detail.*

class ReleaseDetailFragment : BaseFragment(
    R.layout.fragment_release_detail,
    R.menu.release
), DateOpener, UrlOpener, WebSearchOpener {

    private val viewModel by lazy { createViewModel(ReleaseDetailViewModel::class) }

    private val releaseId by lazy {
        ReleaseDetailFragmentArgs.fromBundle(requireArguments()).releaseId
    }

    private lateinit var viewPagerAdapter: ViewPager2FragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.web_search -> { searchInWeb(context, viewModel.release?.titleFull); return true }
            R.id.web_open -> { openUrl(viewModel.release?.externalUrl); return true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.web_open).isVisible = viewModel.release?.externalUrl != null
    }

    private fun init() {
        val fragments = listOf(
            getString(R.string.info) to ReleaseInfoFragment(),
            getString(R.string.images) to ImageListFragment()
        )
        viewPagerAdapter = ViewPager2FragmentAdapter(this, fragments)
    }

    private fun initLayout() {
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.fitSystemWindows()

        wallpaperImageView.setOnClickListener {
            val release = viewModel.release
            val url = release?.videoUrls?.firstOrNull() ?: release?.imageUrlForWallpaper
            openUrl(url)
        }

        fab.setOnClickListener { setSubscription(!(viewModel.release?.isSubscribed.isTrue)) }

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager2(viewPager)
    }

    private fun fetchData() {
        viewModel.observeRelease(releaseId, this) { release -> setRelease(release) }
    }

    private fun setRelease(release: Release?) {
        // FIXME: Find way to prevent reconstruction onResume
        collapsingToolbarLayout.title = release?.title
        wallpaperImageView.setWallpaper(release)
        videoIndicatorView.isVisible = release?.videoUrls?.firstOrNull() != null

        invalidateTint()
        invalidateSubscription()
        invalidateTabs()
        invalidateOptionsMenu()
    }

    private fun invalidateTint() {
        val color = viewModel.color
        val colorDark = viewModel.colorDark
        layoutContainer.setBackgroundResource(colorDark)
        collapsingToolbarLayout.setContentScrimResource(color)
        collapsingToolbarLayout.setStatusBarScrimResource(color)
    }

    private fun invalidateSubscription() {
        val isSubscribed = viewModel.release?.isSubscribed ?: false
        fab.backgroundTintResource = if (isSubscribed) R.color.yellow else viewModel.color
        fab.tintResource = if (isSubscribed) R.color.brown else android.R.color.white
        fab.setImageResource(if (isSubscribed) R.drawable.ic_subscription_on else R.drawable.ic_subscription_off)
    }

    private fun invalidateTabs() {
        viewPagerAdapter.children.forEach { (_, fragment) ->
            (fragment as? ReleaseObserver)?.apply { this.release = viewModel.release }
            (fragment as? ImageUrlObserver)?.apply { this.imageUrls = viewModel.release?.imageUrls }
        }
    }

    private fun setSubscription(isSubscribed: Boolean) {
        viewModel.release?.isSubscribed = isSubscribed
        // FIXME: Replace with Snackbar when being placed behind BottomAppBar
        context?.showToast(if (isSubscribed) R.string.subscription_added else R.string.subscription_removed)
        // Workaround for broken icon: https://issuetracker.google.com/issues/111316656
        fab.hide()
        invalidateSubscription()
        fab.show()
    }

    private fun openUrl(url: String?) {
        val context = context ?: return
        if (url == null) return
        openUrl(context, url)
    }
}