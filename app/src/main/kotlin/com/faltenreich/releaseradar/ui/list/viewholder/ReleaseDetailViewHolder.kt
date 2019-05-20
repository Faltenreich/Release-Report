package com.faltenreich.releaseradar.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import com.faltenreich.releaseradar.extension.screenSize
import com.faltenreich.releaseradar.extension.setImageAsync
import com.faltenreich.releaseradar.extension.tintResource
import com.faltenreich.releaseradar.ui.view.ReleaseOpener
import kotlinx.android.synthetic.main.list_item_release_detail.*

class ReleaseDetailViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<Release>(context, R.layout.list_item_release_detail, parent), ReleaseOpener {

    override fun onBind(data: Release) {
        container.setOnClickListener { openRelease(context, data, releaseCoverImageView) }
        data.imageUrlForThumbnail?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: releaseCoverImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
        releaseTypeImageView.setImageResource(data.mediaType?.iconResId ?: android.R.color.transparent)
        releaseTypeImageView.tintResource = data.mediaType?.colorResId ?: R.color.colorPrimary
        releaseNameTextView.text = data.artistName?.let { artist -> "$artist - ${data.title}" } ?: data.title
        releaseDateTextView.text = data.releaseDateForUi(context)
    }
}