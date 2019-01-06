package com.faltenreich.releaseradar.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Genre
import com.faltenreich.releaseradar.data.model.Platform
import com.faltenreich.releaseradar.data.viewmodel.ReleaseDetailViewModel
import com.faltenreich.releaseradar.extension.print
import com.faltenreich.releaseradar.extension.screenSize
import com.faltenreich.releaseradar.extension.setImageAsync
import com.faltenreich.releaseradar.ui.view.Chip
import kotlinx.android.synthetic.main.fragment_release_detail.*

class ReleaseDetailFragment : BaseFragment(R.layout.fragment_release_detail) {
    private val viewModel by lazy { createViewModel(ReleaseDetailViewModel::class) }
    private val releaseId: String? by lazy { arguments?.let { arguments -> ReleaseDetailFragmentArgs.fromBundle(arguments).releaseId } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    private fun initLayout() {
        context?.apply {
            toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { finish() }

            val transition = TransitionInflater.from(context).inflateTransition(R.transition.shared_element)
            sharedElementEnterTransition = transition
            sharedElementReturnTransition = transition
            ViewCompat.setTransitionName(releaseCoverImageView, SHARED_ELEMENT_NAME)
        }
    }

    private fun initData() {
        releaseId?.let { id ->
            viewModel.observeRelease(id, this) { release ->
                toolbar.title = release?.title

                release?.imageUrlForWallpaper?.let { url ->
                    releaseWallpaperImageView.setImageAsync(url)
                }

                val description = release?.description?.takeIf(String::isNotBlank)
                releaseDescriptionTextView.text = description ?: getString(R.string.unknown_description)
                releaseDescriptionTextView.setTypeface(releaseDescriptionTextView.typeface, if (description != null) Typeface.NORMAL else Typeface.ITALIC)

                release?.let {
                    metaChipContainer.removeAllViews()
                    addChip(metaChipContainer, release.releaseDate?.print(), R.drawable.ic_date)

                    viewModel.observeGenres(release, this) { genres ->
                        genreChipContainer.removeAllViews()
                        genres?.forEach { genre -> addGenre(genre) }
                    }

                    viewModel.observePlatforms(release, this) { platforms ->
                        platforms?.forEach { platform -> addPlatform(platform) }
                    }
                }

                release?.imageUrlForThumbnail?.let { imageUrl ->
                    releaseCoverImageView.setImageAsync(imageUrl, context?.screenSize?.x?.let { width -> width / 2 }) { startPostponedEnterTransition() }
                } ?: startPostponedEnterTransition()

                invalidateTint()
            }
        }
    }

    private fun invalidateTint() {
        viewModel.release?.mediaType?.let { mediaType ->
            val color = mediaType.colorResId
            val colorDark = mediaType.colorDarkResId

            layoutContainer.setBackgroundResource(colorDark)
            appbarLayout.setBackgroundResource(color)
            collapsingToolbarLayout.setContentScrimResource(color)
            collapsingToolbarLayout.setStatusBarScrimResource(color)
        }
    }

    private fun addGenre(genre: Genre) = addChip(genreChipContainer, genre.title)

    private fun addPlatform(platform: Platform) = addChip(metaChipContainer, platform.title)

    private fun addChip(container: ViewGroup, title: String?, @DrawableRes iconResId: Int? = null) = context?.let { context ->
        val chip = Chip(context).apply {
            text = title
            iconResId?.let {
                setChipIconResource(iconResId)
                // FIXME: Breaks chipIconTint
            }
            setChipBackgroundColorResource(viewModel.release?.mediaType?.colorResId ?: R.color.colorPrimary)
        }
        container.addView(chip)
    }

    companion object {
        const val SHARED_ELEMENT_NAME = "cover"
    }
}