package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.backgroundTint
import com.faltenreich.release.extension.isTrue
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.ui.logic.opener.ReleaseOpener
import com.faltenreich.release.ui.logic.provider.ReleaseProvider
import kotlinx.android.synthetic.main.list_item_release_image.*
import org.jetbrains.anko.imageResource

class ReleaseImageViewHolder(
    context: Context,
    parent: ViewGroup,
    private val showDate: Boolean = true
) : BaseViewHolder<ReleaseProvider>(context, R.layout.list_item_release_image, parent), ReleaseOpener {
    override fun onBind(data: ReleaseProvider) {
        val release = data.release
        container.setOnClickListener { openRelease(navigationController, release, releaseCoverImageView) }

        release.imageUrlForThumbnail?.let { imageUrl ->
            releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 )
        } ?: releaseCoverImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)

        releaseTypeImageView.imageResource = release.releaseType?.iconResId ?: android.R.color.transparent
        releaseTypeImageView.backgroundTint = ContextCompat.getColor(context, release.releaseType?.colorResId ?: R.color.colorPrimary)
        releaseFavoriteImageView.visibility = if (release.isFavorite) View.VISIBLE else View.GONE

        releaseTitleTextView.text = release.title
        releaseSubtitleTextView.text = release.subtitle
        releaseSubtitleTextView.isVisible = release.subtitle?.isNotBlank().isTrue
        releaseDateTextView.text = release.releaseDateForUi(context)
        releaseDateTextView.isVisible = showDate
    }
}