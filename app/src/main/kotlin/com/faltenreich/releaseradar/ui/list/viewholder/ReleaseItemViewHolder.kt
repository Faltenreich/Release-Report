package com.faltenreich.releaseradar.ui.list.viewholder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.extension.screenSize
import com.faltenreich.releaseradar.extension.setImageAsync
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseListItem
import com.faltenreich.releaseradar.ui.view.ReleaseOpener
import kotlinx.android.synthetic.main.list_item_release.*

class ReleaseItemViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<ReleaseListItem>(context, R.layout.list_item_release, parent), ReleaseOpener {

    override fun onBind(data: ReleaseListItem) {
        data.release?.let { release ->
            cardView.setOnClickListener { openRelease(context, release, releaseCoverImageView) }
            release.imageUrlForThumbnail?.let { imageUrl -> releaseCoverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 ) } ?: releaseCoverImageView.setImageResource(android.R.color.transparent)
            releaseTypeImageView.setImageResource(release.mediaType?.iconResId ?: android.R.color.transparent)
            releaseTypeImageView.background = ContextCompat.getDrawable(context, R.drawable.dogear_top_start)?.apply { setTint(ContextCompat.getColor(context, release.mediaType?.colorResId ?: R.color.colorPrimary)) }
            releaseNameTextView.text = release.artistName?.let { artist -> "$artist - ${release.title}" } ?: release.title
            releaseFavoriteImageView.visibility = if (release.isFavorite) View.VISIBLE else View.GONE
        }
    }
}