package com.faltenreich.release.domain.release.detail

import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.faltenreich.release.R
import com.faltenreich.release.base.intent.UrlOpener
import com.faltenreich.release.base.intent.WebSearchOpener
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.date.DateOpener
import com.faltenreich.release.domain.release.setCover
import com.faltenreich.release.domain.release.setWallpaper
import com.faltenreich.release.framework.android.context.showToast
import com.faltenreich.release.framework.android.decoration.GridLayoutSpacingItemDecoration
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.fragment.invalidateOptionsMenu
import com.faltenreich.release.framework.android.view.backgroundTintResource
import com.faltenreich.release.framework.android.view.tintResource
import kotlinx.android.synthetic.main.fragment_release_detail.*

class ReleaseDetailFragment : BaseFragment(
    R.layout.fragment_release_detail,
    R.menu.release
), DateOpener, UrlOpener, WebSearchOpener {

    private val viewModel by lazy { createViewModel(ReleaseDetailViewModel::class) }

    private val releaseId: String? by lazy {
        arguments?.let { arguments ->
            ReleaseDetailFragmentArgs.fromBundle(arguments).releaseId
        }
    }

    private lateinit var imageListAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
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
        imageListAdapter = ImageListAdapter(requireContext())
    }

    private fun initLayout() {
        val context = context ?: return

        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        ViewCompat.setTransitionName(coverImageView,
            SHARED_ELEMENT_NAME
        )

        toolbar.setNavigationOnClickListener { finish() }
        // Workaround: Fixing fitsSystemWindows programmatically
        toolbar.doOnPreDraw {
            val frame = Rect()
            activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
            toolbar.layoutParams.height = toolbar.height + frame.top
            toolbar.setPadding(0, frame.top, 0, 0)
        }

        wallpaperImageView.setOnClickListener {
            val release = viewModel.release
            val url = release?.videos?.firstOrNull() ?: release?.imageUrlForWallpaper
            openUrl(url)
        }

        coverImageView.setOnClickListener { openUrl(viewModel.release?.imageUrlForCover) }
        fab.setOnClickListener { setSubscription(!(viewModel.release?.isSubscribed.isTrue)) }
        dateChip.setOnClickListener { viewModel.release?.releaseDate?.let { date -> openDate(findNavController(), date) } }

        imageListView.layoutManager = GridLayoutManager(context, 3)
        imageListView.addItemDecoration(GridLayoutSpacingItemDecoration(context))
        imageListView.adapter = imageListAdapter
    }

    private fun initData() {
        releaseId?.let { id ->
            viewModel.observeRelease(id, this) { release -> setRelease(release) }
            viewModel.observeGenres(this) { genres -> addGenres(genres ?: listOf()) }
            viewModel.observePlatforms(this) { platforms -> addPlatforms(platforms ?: listOf()) }
        }
    }

    private fun invalidateTint() {
        val color = viewModel.color
        val colorDark = viewModel.colorDark
        layoutContainer.setBackgroundResource(colorDark)
        collapsingToolbarLayout.setContentScrimResource(color)
        collapsingToolbarLayout.setStatusBarScrimResource(color)
    }

    private fun invalidateSubscriptions() {
        val isSubscribed = viewModel.release?.isSubscribed ?: false
        fab.backgroundTintResource = if (isSubscribed) R.color.yellow else viewModel.color
        fab.tintResource = if (isSubscribed) R.color.brown else android.R.color.white
        fab.setImageResource(if (isSubscribed) R.drawable.ic_subscription_on else R.drawable.ic_subscription_off)
    }

    private fun invalidateImages() {
        val release = viewModel.release
        val imageUrls = release?.images ?: listOf()
        imageListAdapter.removeListItems()
        imageListAdapter.addListItems(imageUrls)
        imageListAdapter.notifyDataSetChanged()
    }

    private fun setRelease(release: Release?) {
        val context = context ?: return

        collapsingToolbarLayout.title = release?.title
        titleTextView.text = release?.title
        artistTextView.text = release?.artist
        artistTextView.isVisible = artistTextView.text.isNotBlank()
        descriptionTextView.text = release?.description ?: getString(R.string.unknown_description)
        descriptionTextView.setTypeface(
            descriptionTextView.typeface,
            if (release?.description != null) Typeface.NORMAL else Typeface.ITALIC
        )

        wallpaperImageView.setWallpaper(release)
        coverImageView.setCover(release) {  startPostponedEnterTransition() }

        dateChip.text = release?.releaseDateForUi(context)
        dateChip.setChipBackgroundColorResource(release?.releaseType?.colorResId ?: R.color.colorPrimary)

        videoIndicatorView.isVisible = release?.videos?.firstOrNull() != null

        invalidateTint()
        invalidateSubscriptions()
        invalidateImages()
        invalidateOptionsMenu()
    }

    private fun addPlatforms(platforms: List<Platform>) {
        platformChipContainer.removeAllViews()
        platforms.forEach { platform -> addChip(platformChipContainer, platform.title) }
        platformChipScrollContainer.isVisible = platforms.isNotEmpty()
    }

    private fun addGenres(genres: List<Genre>) {
        genreChipContainer.removeAllViews()
        genres.forEach { genre -> addChip(genreChipContainer, genre.title) }
        genreChipScrollContainer.isVisible = genres.isNotEmpty()
    }

    private fun addChip(
        container: ViewGroup,
        title: String?,
        @DrawableRes iconResId: Int? = null,
        onClick: (() -> Unit)? = null
    ) {
        val context = context ?: return
        val chip = ChipView(context).apply {
            text = title
            iconResId?.let { setChipIconResource(iconResId) }
            setChipBackgroundColorResource(viewModel.release?.releaseType?.colorResId ?: R.color.colorPrimary)
            setOnClickListener { onClick?.invoke() }
        }
        container.addView(chip)
    }

    private fun setSubscription(isSubscribed: Boolean) {
        viewModel.release?.isSubscribed = isSubscribed
        // FIXME: Replace with Snackbar when being placed behind BottomAppBar
        context?.showToast(if (isSubscribed) R.string.subscription_added else R.string.subscription_removed)
        // Workaround for broken icon: https://issuetracker.google.com/issues/111316656
        fab.hide()
        invalidateSubscriptions()
        fab.show()
    }

    private fun openUrl(url: String?) {
        val context = context ?: return
        if (url == null) return
        openUrl(context, url)
    }

    companion object {
        const val SHARED_ELEMENT_NAME = "cover"
    }
}