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
import androidx.transition.TransitionInflater
import com.faltenreich.release.R
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.date.DateOpener
import com.faltenreich.release.framework.android.context.screenSize
import com.faltenreich.release.framework.android.context.showToast
import com.faltenreich.release.framework.android.fragment.BaseFragment
import com.faltenreich.release.framework.android.fragment.invalidateOptionsMenu
import com.faltenreich.release.framework.android.view.backgroundTintResource
import com.faltenreich.release.framework.android.view.setImageAsync
import com.faltenreich.release.framework.android.view.tintResource
import kotlinx.android.synthetic.main.fragment_release_detail.*

class ReleaseDetailFragment : BaseFragment(R.layout.fragment_release_detail, R.menu.release),
    DateOpener, UrlOpener {
    private val viewModel by lazy { createViewModel(ReleaseDetailViewModel::class) }
    private val releaseId: String? by lazy { arguments?.let { arguments -> ReleaseDetailFragmentArgs.fromBundle(
        arguments
    ).releaseId } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.open -> { openUrl(viewModel.release?.externalUrl); return true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.open).isVisible = viewModel.release?.externalUrl != null
    }

    private fun initLayout() {
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        ViewCompat.setTransitionName(releaseCoverImageView,
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

        releaseWallpaperImageView.setOnClickListener { openUrl(viewModel.release?.imageUrlForWallpaper) }
        releaseCoverImageView.setOnClickListener { openUrl(viewModel.release?.imageUrlForCover) }
        fab.setOnClickListener { setFavorite(!(viewModel.release?.isFavorite.isTrue)) }
        dateChip.setOnClickListener { viewModel.release?.releaseDate?.let { date -> openDate(findNavController(), date) } }
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

    private fun invalidateFavorite() {
        val isFavorite = viewModel.release?.isFavorite ?: false
        fab.backgroundTintResource = if (isFavorite) R.color.yellow else viewModel.color
        fab.tintResource = if (isFavorite) R.color.brown else android.R.color.white
        fab.setImageResource(if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
    }

    private fun setRelease(release: Release?) {
        context?.let { context ->
            collapsingToolbarLayout.title = release?.title
            releaseTitleTextView.text = release?.title
            releaseSubtitleTextView.text = release?.subtitle
            releaseSubtitleTextView.isVisible = release?.subtitle?.isNotBlank().isTrue
            releaseDescriptionTextView.text = release?.description ?: getString(R.string.unknown_description)
            releaseDescriptionTextView.setTypeface(releaseSubtitleTextView.typeface, if (release?.description != null) Typeface.NORMAL else Typeface.ITALIC)

            release?.imageUrlForWallpaper?.let { url ->
                releaseWallpaperImageView.setImageAsync(url)
            }

            release?.imageUrlForThumbnail?.let { imageUrl ->
                releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2) { startPostponedEnterTransition() }
            } ?: startPostponedEnterTransition()

            dateChip.text = release?.releaseDateForUi(context)
            dateChip.setChipBackgroundColorResource(release?.releaseType?.colorResId ?: R.color.colorPrimary)

            invalidateTint()
            invalidateFavorite()
            invalidateOptionsMenu()
        }
    }

    private fun addGenres(genres: List<Genre>) {
        genreChipContainer.removeAllViews()
        genres.forEach { genre -> addChip(genreChipContainer, genre.title) }
    }

    private fun addPlatforms(platforms: List<Platform>) {
        platformChipContainer.removeAllViews()
        platforms.forEach { platform -> addChip(platformChipContainer, platform.title) }
    }

    private fun addChip(container: ViewGroup, title: String?, @DrawableRes iconResId: Int? = null, onClick: (() -> Unit)? = null) = context?.let { context ->
        val chip = ChipView(context).apply {
            text = title
            iconResId?.let { setChipIconResource(iconResId) }
            setChipBackgroundColorResource(viewModel.release?.releaseType?.colorResId ?: R.color.colorPrimary)
            setOnClickListener { onClick?.invoke() }
        }
        container.addView(chip)
    }

    private fun setFavorite(isFavorite: Boolean) {
        viewModel.release?.isFavorite = isFavorite
        context?.showToast(if (isFavorite) R.string.favorite_added else R.string.favorite_removed)
        // Workaround for broken icon: https://issuetracker.google.com/issues/111316656
        fab.hide()
        invalidateFavorite()
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