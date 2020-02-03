package com.faltenreich.release.domain.release.detail

import android.graphics.Typeface
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
import com.faltenreich.release.framework.android.context.showToast
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
        coverImageView.setOnClickListener { openImage(findNavController(), viewModel.release, viewModel.release?.imageUrlForCover) }
        dateChip.setOnClickListener { openDate() }
        popularityChip.setOnClickListener { context?.showToast(getString(R.string.popularity)) }
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
        descriptionTextView.text = release?.description ?: getString(R.string.unknown_description)
        descriptionTextView.setTypeface(
            descriptionTextView.typeface,
            if (release?.description != null) Typeface.NORMAL else Typeface.ITALIC
        )

        val chipColor = release?.releaseType?.colorResId ?: R.color.colorPrimary

        dateChip.text = release?.releaseDateForUi(context)
        dateChip.setChipBackgroundColorResource(chipColor)

        popularityChip.text = release?.popularity?.toInt()?.toString() ?: "-"
        popularityChip.setChipBackgroundColorResource(chipColor)
        popularityChip.setChipIconResource(release?.popularityRating?.iconRes ?: PopularityRating.LOW.iconRes)
    }

    private fun setPlatforms(platforms: List<Platform>?) {
        platformChipContainer.removeAllViews()
        platforms?.forEach { platform -> addChip(platformChipContainer, platform.title) }
        platformChipScrollContainer.isVisible = platforms?.isNotEmpty().isTrue
    }

    private fun setGenres(genres: List<Genre>?) {
        genreChipContainer.removeAllViews()
        genres?.forEach { genre -> addChip(genreChipContainer, genre.title) }
        genreChipScrollContainer.isVisible = genres?.isNotEmpty().isTrue
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

    private fun openDate() {
        val date = viewModel.release?.releaseDate ?: return
        openDate(findNavController(), date)
    }
}