package com.faltenreich.release.domain.release.detail

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.faltenreich.release.R
import com.faltenreich.release.base.intent.UrlOpener
import com.faltenreich.release.data.enum.PopularityRating
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.data.model.Platform
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.date.DateOpener
import com.faltenreich.release.domain.release.setCover
import com.faltenreich.release.framework.android.context.showToast
import com.faltenreich.release.framework.android.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_release_info.*

class ReleaseInfoFragment : BaseFragment(
    R.layout.fragment_release_info
), UrlOpener, DateOpener {

    private val viewModel by lazy { createSharedViewModel(ReleaseInfoViewModel::class) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        fetchData()
    }

    private fun initLayout() {
        coverImageView.setOnClickListener { openUrl(viewModel.release?.imageUrlForCover) }
        dateChip.setOnClickListener { openDate() }
        popularityChip.setOnClickListener { showPopularity() }
    }

    private fun fetchData() {
        viewModel.observeRelease(this) { release -> setMetadata(release) }
        viewModel.observePlatforms(this) { platforms -> setPlatforms(platforms ?: listOf()) }
        viewModel.observeGenres(this) { genres -> setGenres(genres ?: listOf()) }
    }

    private fun setMetadata(release: Release?) {
        coverImageView.setCover(release) {  startPostponedEnterTransition() }

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

    private fun setPlatforms(platforms: List<Platform>) {
        platformChipContainer.removeAllViews()
        platforms.forEach { platform -> addChip(platformChipContainer, platform.title) }
        platformChipScrollContainer.isVisible = platforms.isNotEmpty()
    }

    private fun setGenres(genres: List<Genre>) {
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

    private fun openUrl(url: String?) {
        val context = context ?: return
        if (url == null) return
        openUrl(context, url)
    }

    private fun openDate() {
        val date = viewModel.release?.releaseDate ?: return
        openDate(findNavController(), date)
    }

    private fun showPopularity() {
        val popularity = viewModel.release?.popularity ?: 0f
        context?.showToast("%s: %d".format(getString(R.string.popularity), popularity.toInt()))
    }
}