package com.faltenreich.release.domain.release.list

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.framework.android.context.screenSize
import com.faltenreich.release.framework.android.view.setImageAsync
import com.faltenreich.release.framework.android.view.tintResource
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_release_detail.*

class ReleaseDetailViewHolder(
    context: Context,
    parent: ViewGroup,
    private val showDate: Boolean
) : BaseViewHolder<ReleaseProvider>(context, R.layout.list_item_release_detail, parent),
    ReleaseOpener {
    override fun onBind(data: ReleaseProvider) {
        val release = data.release
        container.setOnClickListener { openRelease(navigationController, release, releaseCoverImageView) }

        releaseDateTextView.text = release.releaseDateForUi(context)
        releaseDateTextView.isVisible = showDate

        release.imageUrlForThumbnail?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: releaseCoverImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
        releaseTypeImageView.setImageResource(release.releaseType?.iconResId ?: android.R.color.transparent)
        releaseTypeImageView.tintResource = release.releaseType?.colorResId ?: R.color.colorPrimary

        releaseTitleTextView.text = release.title
        releaseSubtitleTextView.text = release.subtitle
        releaseSubtitleTextView.isVisible = release.subtitle?.isNotBlank().isTrue
        releaseDescriptionTextView.text = release.description

        releaseFavoriteImageView.isVisible = release.isFavorite
    }
}