package com.faltenreich.release.ui.fragment

import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.data.viewmodel.ReleaseDetailViewModel
import com.faltenreich.release.extension.*
import com.faltenreich.release.ui.list.adapter.GalleryListAdapter
import com.faltenreich.release.ui.logic.opener.DateOpener
import com.faltenreich.release.ui.logic.opener.MediaOpener
import com.faltenreich.release.ui.view.Chip
import kotlinx.android.synthetic.main.fragment_release_detail.*

class ReleaseDetailFragment : BaseFragment(R.layout.fragment_release_detail, R.menu.release), DateOpener, MediaOpener {
    private val viewModel by lazy { createViewModel(ReleaseDetailViewModel::class) }
    private val releaseId: String? by lazy { arguments?.let { arguments -> ReleaseDetailFragmentArgs.fromBundle(arguments).releaseId } }

    private val listAdapter by lazy { context?.let { context -> GalleryListAdapter(context) } }
    private lateinit var listLayoutManager: StaggeredGridLayoutManager

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
            R.id.open -> { openExternally(); return true }
            R.id.share -> { share(); return true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initLayout() {
        context?.apply {
            initTransition()
            initToolbar()
            releaseWallpaperImageView.setOnClickListener { openMedia(releaseWallpaperImageView) }
            releaseCoverImageView.setOnClickListener { openMedia(releaseCoverImageView) }
            fab.setOnClickListener { setFavorite(!(viewModel.release?.isFavorite ?: false)) }
            initList()
        }
    }

    private fun initTransition() {
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        // TODO: sharedElementExitTransition is not working
        ViewCompat.setTransitionName(releaseCoverImageView, SHARED_ELEMENT_NAME)
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener { finish() }
        // Workaround: Fixing fitsSystemWindows programmatically
        toolbar.doOnPreDraw {
            val frame = Rect()
            activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
            toolbar.layoutParams.height = toolbar.height + frame.top
            toolbar.setPadding(0, frame.top, 0, 0)
        }
    }

    private fun initList() {
        listLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        galleryListView.layoutManager = listLayoutManager
        galleryListView.adapter = listAdapter
    }

    private fun initData() {
        releaseId?.let { id ->
            // TODO: Change order of observation to prevent scrambled and jumping layout
            viewModel.observeRelease(id, this) { release -> setRelease(release) }
            viewModel.observeGenres(this) { genres -> addGenres(genres ?: listOf()) }
            viewModel.observePlatforms(this) { platforms -> addPlatforms(platforms ?: listOf()) }
            viewModel.observeMedia(this) { media ->  }
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
        collapsingToolbarLayout.title = release?.title
        releaseTitleTextView.text = release?.title

        release?.imageUrlForWallpaper?.let { url ->
            releaseWallpaperImageView.setImageAsync(url)
        }

        val description = release?.description?.takeIf(String::isNotBlank)
        releaseDescriptionTextView.text = description ?: getString(com.faltenreich.release.R.string.unknown_description)
        releaseDescriptionTextView.setTypeface(releaseDescriptionTextView.typeface, if (description != null) Typeface.NORMAL else Typeface.ITALIC)

        release?.let {
            metaChipContainer.removeAllViews()
            addChip(
                metaChipContainer,
                release.releaseDateForUi(context),
                R.drawable.ic_date,
                onClick = { release.releaseDate?.let { date -> openDate(findNavController(), date) } }
            )
        }

        release?.imageUrlForThumbnail?.let { imageUrl ->
            releaseCoverImageView.setImageAsync(imageUrl, context?.screenSize?.x?.let { width -> width / 2 }) { startPostponedEnterTransition() }
        } ?: startPostponedEnterTransition()

        invalidateTint()
        invalidateFavorite()
    }

    private fun addGenres(genres: List<Genre>) {
        genreChipContainer.removeAllViews()
        genres.forEach { genre -> addChip(genreChipContainer, genre.title) }
    }

    private fun addPlatforms(platforms: List<Platform>) {
        platforms.forEach { platform -> addChip(metaChipContainer, platform.title) }
    }

    private fun addChip(container: ViewGroup, title: String?, @DrawableRes iconResId: Int? = null, onClick: (() -> Unit)? = null) = context?.let { context ->
        val chip = Chip(context).apply {
            text = title
            iconResId?.let { setChipIconResource(iconResId) }
            setChipBackgroundColorResource(viewModel.release?.releaseType?.colorResId ?: R.color.colorPrimary)
            setOnClickListener { onClick?.invoke() }
        }
        container.addView(chip)
    }

    private fun addMedia(media: List<Media>) {
        listAdapter?.removeListItems()
        listAdapter?.addListItems(media)
        listAdapter?.notifyDataSetChanged()
    }

    private fun setFavorite(isFavorite: Boolean) {
        viewModel.release?.isFavorite = isFavorite
        context?.showToast(if (isFavorite) R.string.favorite_added else R.string.favorite_removed)
        // Workaround for broken icon: https://issuetracker.google.com/issues/111316656
        fab.hide()
        invalidateFavorite()
        fab.show()
    }

    private fun openMedia(sharedElement: View) {
        viewModel.release?.let { release ->
            appNavigationController?.let { navigationController ->
                openMedia(navigationController, release, null, sharedElement)
            }
        }
    }

    private fun openExternally() {

    }

    private fun share() {

    }

    companion object {
        const val SHARED_ELEMENT_NAME = "cover"
    }
}