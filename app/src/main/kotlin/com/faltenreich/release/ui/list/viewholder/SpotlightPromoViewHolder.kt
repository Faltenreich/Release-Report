package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.ui.list.item.SpotlightPromoItem
import com.faltenreich.release.ui.view.ReleaseOpener

class SpotlightPromoViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<SpotlightPromoItem>(context, R.layout.list_item_release_image, parent), ReleaseOpener {

    override fun onBind(data: SpotlightPromoItem) {

    }
}