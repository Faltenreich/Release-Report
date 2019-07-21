package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.extension.screenSize
import com.faltenreich.release.extension.setImageAsync
import com.faltenreich.release.extension.tintResource
import com.faltenreich.release.ui.logic.opener.ReleaseOpener
import com.faltenreich.release.ui.logic.provider.ReleaseProvider
import kotlinx.android.synthetic.main.list_item_release_detail.*

class ReleaseDetailViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<ReleaseProvider>(context, R.layout.list_item_release_detail, parent), ReleaseOpener {
    override fun onBind(data: ReleaseProvider) {
        val release = data.release
        container.setOnClickListener { openRelease(navigationController, release, releaseCoverImageView) }
        release.imageUrlForThumbnail?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: releaseCoverImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
        releaseTypeImageView.setImageResource(release.releaseType?.iconResId ?: android.R.color.transparent)
        releaseTypeImageView.tintResource = release.releaseType?.colorResId ?: R.color.colorPrimary
        releaseTitleTextView.text = release.artistName?.let { artist -> "$artist - ${release.title}" } ?: release.title
        labelTextView.text = release.releaseDateForUi(context)
    }
}