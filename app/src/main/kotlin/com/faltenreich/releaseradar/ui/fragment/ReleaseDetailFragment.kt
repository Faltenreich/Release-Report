package com.faltenreich.releaseradar.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Genre
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.data.print
import com.faltenreich.releaseradar.data.viewmodel.ReleaseDetailViewModel
import com.faltenreich.releaseradar.setImageAsync
import com.faltenreich.releaseradar.ui.view.Chip
import kotlinx.android.synthetic.main.fragment_release_detail.*

class ReleaseDetailFragment : BaseFragment(R.layout.fragment_release_detail) {

    private val viewModel by lazy { createViewModel(ReleaseDetailViewModel::class) }
    private val releaseId: String? by lazy { ReleaseDetailFragmentArgs.fromBundle(arguments).releaseId }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initData()
    }

    private fun initLayout() {
        context?.let { context ->
            toolbar.navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { finish() }
        }
    }

    private fun initData() {
        releaseId?.let { id ->
            viewModel.observeRelease(id, this) { release ->
                toolbar.title = release?.name

                release?.imageUrlForWallpaper?.let { url -> releaseWallpaperImageView.setImageAsync(url) } ?: releaseWallpaperImageView.setImageResource(android.R.color.transparent)
                release?.mediaType?.let { mediaType -> collapsingToolbarLayout.setContentScrimResource(mediaType.colorResId) }

                val description = release?.description?.takeIf(String::isNotBlank)
                releaseDescriptionTextView.text = description ?: getString(R.string.unknown_content)
                releaseDescriptionTextView.setTypeface(releaseDescriptionTextView.typeface, if (description != null) Typeface.NORMAL else Typeface.ITALIC)

                release?.let {
                    addMeta(release)

                    viewModel.observeGenres(release, this) { genres ->
                        genreChipContainer.removeAllViews()
                        genres?.forEach { genre -> addGenre(genre) }
                    }
                }
            }
        }
    }

    private fun addMeta(release: Release) {
        addChip(metaChipContainer, release.releaseDate?.print() ?: getString(R.string.placeholder), R.drawable.ic_date)
        addChip(metaChipContainer, release.durationInSeconds?.let { durationInSeconds -> getString(R.string.duration_placeholder).format(durationInSeconds) } ?: getString(R.string.placeholder), R.drawable.ic_duration)
    }

    private fun addGenre(genre: Genre) = addChip(genreChipContainer, genre.name)

    private fun addChip(container: ViewGroup, title: String?, @DrawableRes iconResId: Int? = null) = context?.let { context ->
        val chip = Chip(context).apply {
            text = title
            iconResId?.let {
                setChipIconResource(iconResId)
                // FIXME: Breaks chipIconTint
            }
        }
        container.addView(chip)
    }
}