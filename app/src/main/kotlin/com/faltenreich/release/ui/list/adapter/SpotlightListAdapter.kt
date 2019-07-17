package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.SpotlightItem
import com.faltenreich.release.ui.list.item.SpotlightLabelItem
import com.faltenreich.release.ui.list.item.SpotlightPromoItem
import com.faltenreich.release.ui.list.item.SpotlightReleaseItem
import com.faltenreich.release.ui.list.viewholder.SpotlightLabelViewHolder
import com.faltenreich.release.ui.list.viewholder.SpotlightPromoViewHolder
import com.faltenreich.release.ui.list.viewholder.SpotlightReleaseViewHolder
import com.faltenreich.release.ui.list.viewholder.SpotlightViewHolder

class SpotlightListAdapter(context: Context) : SimpleListAdapter<SpotlightItem, SpotlightViewHolder<SpotlightItem>>(context) {

    override fun getItemViewType(position: Int): Int {
        // FIXME: Seal class
        return when (val item = getListItemAt(position)) {
            is SpotlightLabelItem -> VIEW_TYPE_LABEL
            is SpotlightReleaseItem -> VIEW_TYPE_RELEASE
            is SpotlightPromoItem -> VIEW_TYPE_PROMO
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotlightViewHolder<SpotlightItem> {
        return when (viewType) {
            VIEW_TYPE_LABEL -> SpotlightLabelViewHolder(context, parent)
            VIEW_TYPE_RELEASE -> SpotlightReleaseViewHolder(context, parent)
            VIEW_TYPE_PROMO -> SpotlightPromoViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        } as SpotlightViewHolder<SpotlightItem>
    }

    companion object {
        const val VIEW_TYPE_LABEL = 0
        const val VIEW_TYPE_RELEASE = 1
        const val VIEW_TYPE_PROMO = 2
    }
}