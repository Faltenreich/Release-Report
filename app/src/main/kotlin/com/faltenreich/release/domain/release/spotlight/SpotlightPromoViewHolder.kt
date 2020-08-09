package com.faltenreich.release.domain.release.spotlight

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.domain.release.setWallpaper
import com.faltenreich.release.framework.android.view.recyclerview.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_release_promo.*

class SpotlightPromoViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<SpotlightPromoItem>(context, R.layout.list_item_release_promo, parent),
    ReleaseOpener {
    override fun onBind(data: SpotlightPromoItem) {
        val release = data.release
        container.setOnClickListener { openRelease(navigationController, release) }
        dateTextView.text = release.releaseDateForUi(context)
        titleTextView.text = release.titleFull
        imageView.setWallpaper(release)
    }
}