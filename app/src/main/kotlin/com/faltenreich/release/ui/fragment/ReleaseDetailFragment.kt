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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.viewmodel.ReleaseDetailViewModel
import com.faltenreich.release.extension.*
import com.faltenreich.release.ui.list.adapter.GalleryListAdapter
import com.faltenreich.release.ui.view.Chip
import kotlinx.android.synthetic.main.fragment_release_detail.*


class ReleaseDetailFragment : BaseFragment(R.layout.fragment_release_detail) {
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
            val transition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element)
            sharedElementEnterTransition = transition
            sharedElementReturnTransition = transition
            ViewCompat.setTransitionName(releaseCoverImageView, SHARED_ELEMENT_NAME)

            toolbar.setNavigationOnClickListener { finish() }
            // Workaround: Fixing fitsSystemWindows programmatically
            toolbar.doOnPreDraw {
                val frame = Rect()
                activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
                toolbar.layoutParams.height = toolbar.height + frame.top
                toolbar.setPadding(0, frame.top, 0, 0)
            }

            fab.setOnClickListener { setFavorite(!(viewModel.release?.isFavorite ?: false)) }

            listLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            galleryListView.layoutManager = listLayoutManager
            galleryListView.adapter = listAdapter
        }
    }

    private fun initData() {
        releaseId?.let { id ->
            viewModel.observeRelease(id, this) { release ->
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
                    addChip(metaChipContainer, release.releaseDateForUi(context), R.drawable.ic_date)

                    // Change order of observation to prevent scrambled and jumping layout
                    viewModel.observeGenres(release, this) { genres -> genreChipContainer.removeAllViews(); genres?.forEach { genre -> addGenre(genre) } }
                    viewModel.observePlatforms(release, this) { platforms -> platforms?.forEach { platform -> addPlatform(platform) } }
                    // TODO: viewModel.observeImages(release, this) { media -> addImages(media ?: listOf()) }
                }

                release?.imageUrlForThumbnail?.let { imageUrl ->
                    releaseCoverImageView.setImageAsync(imageUrl, context?.screenSize?.x?.let { width -> width / 2 }) { startPostponedEnterTransition() }
                } ?: startPostponedEnterTransition()

                invalidateTint()
                invalidateFavorite()
            }
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

    private fun addGenre(genre: Genre) {
        addChip(genreChipContainer, genre.title)
    }

    private fun addPlatform(platform: Platform) {
        addChip(metaChipContainer, platform.title)
    }

    private fun addChip(container: ViewGroup, title: String?, @DrawableRes iconResId: Int? = null) = context?.let { context ->
        val chip = Chip(context).apply {
            text = title
            iconResId?.let { setChipIconResource(iconResId) }
            setChipBackgroundColorResource(viewModel.release?.releaseType?.colorResId ?: R.color.colorPrimary)
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
        view?.showSnackbar(if (isFavorite) R.string.favorite_added else R.string.favorite_removed)
        // Workaround for broken icon: https://issuetracker.google.com/issues/111316656
        fab.hide()
        invalidateFavorite()
        fab.show()
    }

    private fun openExternally() {

    }

    private fun share() {

    }

    companion object {
        const val SHARED_ELEMENT_NAME = "cover"
    }
}