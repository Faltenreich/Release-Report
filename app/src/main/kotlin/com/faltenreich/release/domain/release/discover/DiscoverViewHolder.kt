package com.faltenreich.release.domain.release.discover

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.framework.android.context.screenSize
import com.faltenreich.release.framework.android.view.backgroundTint
import com.faltenreich.release.framework.android.view.setImageAsync
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_release_image.*
import org.jetbrains.anko.imageResource

class DiscoverViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<ReleaseProvider>(context, R.layout.list_item_release_image, parent),
    ReleaseOpener {
    override fun onBind(data: ReleaseProvider) {
        val release = data.release
        container.setOnClickListener { openRelease(navigationController, release, coverImageView) }

        release.imageUrlForThumbnail?.let { imageUrl ->
            coverImageView.setImageAsync(imageUrl, context.screenSize.x / 2 )
        } ?: coverImageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)

        typeImageView.imageResource = release.releaseType?.iconResId ?: android.R.color.transparent
        typeImageView.backgroundTint = ContextCompat.getColor(context, release.releaseType?.colorResId ?: R.color.colorPrimary)
        subscriptionImageView.visibility = if (release.isSubscribed) View.VISIBLE else View.GONE

        artistTextView.text = release.artist
        titleTextView.text = release.title
    }
}