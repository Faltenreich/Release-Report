package com.faltenreich.release.domain.release.detail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.faltenreich.release.R
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.enum.PopularityRating
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.date.DateOpener
import com.faltenreich.release.domain.release.ReleaseImageOpener
import com.faltenreich.release.domain.release.setCover
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_release_info.*

class ReleaseInfoFragment : BaseFragment(
    R.layout.fragment_release_info
), DateOpener, ReleaseImageOpener {

    private val viewModel by activityViewModels<ReleaseInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun initLayout() {
        coverImageView.setOnClickListener {
            openImage(
                findNavController(),
                viewModel.release,
                viewModel.release?.imageUrlForCover
            )
        }
        dateChip.setOnClickListener { openDate() }
    }

    private fun fetchData() {
        viewModel.observeRelease(this, ::setMetadata)
        viewModel.observePlatforms(this, ::setPlatforms)
        viewModel.observeGenres(this, ::setGenres)
    }

    private fun setMetadata(release: Release?) {
        coverImageView.setCover(release)

        titleTextView.text = release?.title
        artistTextView.text = release?.artist
        artistTextView.isVisible = artistTextView.text.isNotBlank()
        descriptionTextView.text = release?.description?.takeIf(String::isNotBlank)
            ?: getString(R.string.unknown_description)

        setType(release)
        setPopularity(release)
        setDate(release)
    }

    private fun setDate(release: Release?) {
        dateChip.text = release?.releaseDateForUi(context)
        dateChip.setChipBackgroundColorResource(viewModel.color)
    }

    private fun setType(release: Release?) {
        val type = release?.releaseType ?: return
        typeChip.text = getString(type.singularStringRes)
        typeChip.setChipIconResource(type.iconResId)
        typeChip.setChipBackgroundColorResource(viewModel.color)
    }

    private fun setPopularity(release: Release?) {
        val popularity = release?.popularity ?: 0f
        popularityChip.text = "%.0f°".format(popularity)
        popularityChip.setChipIconResource(PopularityRating.ofPopularity(popularity).iconRes)
        popularityChip.setChipBackgroundColorResource(viewModel.color)
    }

    private fun setPlatforms(platforms: List<Platform>?) {
        platformChipContainer.removeAllViews()
        platformChipContainer.isVisible = platforms?.isNotEmpty().isTrue
        platforms?.forEach { platform -> addChip(platformChipContainer, platform.title) }
    }

    private fun setGenres(genres: List<Genre>?) {
        genreChipContainer.removeAllViews()
        genreChipContainer.isVisible = genres?.isNotEmpty().isTrue
        genres?.forEach { genre -> addChip(genreChipContainer, genre.title) }
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
            setChipBackgroundColorResource(
                viewModel.release?.releaseType?.colorResId ?: R.color.colorPrimary
            )
            setOnClickListener { onClick?.invoke() }
        }
        container.addView(chip)
    }

    private fun openDate() {
        val date = viewModel.release?.releaseDate ?: return
        openDate(findNavController(), date)
    }
}