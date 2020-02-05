package com.faltenreich.release.domain.release.detail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
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

    private val viewModel by lazy { createSharedViewModel(ReleaseInfoViewModel::class) }

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

        metaChipContainer.removeAllViews()
        setType(release)
        setDate(release)
    }

    private fun setType(release: Release?) {
        val type = release?.releaseType ?: return
        addChip(
            metaChipContainer,
            title = getString(type.singularStringRes),
            iconResId = type.iconResId
        )
    }

    private fun setDate(release: Release?) {
        val date = release?.releaseDateForUi(context) ?: return
        addChip(
            metaChipContainer,
            title = date,
            iconResId = R.drawable.ic_date,
            onClick = ::openDate
        )
    }

    // TODO: Where to place?
    private fun setPopularity(release: Release?) {
        val popularity = release?.popularity ?: return
        addChip(
            metaChipContainer,
            title = popularity.toInt().toString(),
            iconResId = PopularityRating.ofPopularity(popularity).iconRes,
            onClick = { mainViewModel.showMessage(getString(R.string.popularity)) }
        )
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