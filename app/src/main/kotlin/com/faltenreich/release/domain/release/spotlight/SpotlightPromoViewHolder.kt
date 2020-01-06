package com.faltenreich.release.domain.release.spotlight

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.base.primitive.isTrue
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.framework.android.view.setImageAsync
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_release_promo.*

class SpotlightPromoViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<SpotlightPromoItem>(context, R.layout.list_item_release_promo, parent),
    ReleaseOpener {
    override fun onBind(data: SpotlightPromoItem) {
        val release = data.release
        container.setOnClickListener { openRelease(navigationController, release) }

        dateView.text = release.releaseDateForUi(context)
        titleView.text = release.title
        artistView.text = release.artist
        artistView.isVisible = release.artist?.isNotBlank().isTrue

        release.imageUrlForWallpaper?.let { imageUrl ->
            imageView.setImageAsync(imageUrl)
        } ?: imageView.setImageResource(Release.FALLBACK_COVER_COLOR_RES)
    }
}