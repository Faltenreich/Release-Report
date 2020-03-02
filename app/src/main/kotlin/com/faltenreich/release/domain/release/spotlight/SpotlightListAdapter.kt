package com.faltenreich.release.domain.release.spotlight

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.framework.android.recyclerview.viewholder.BaseViewHolder
import com.faltenreich.release.framework.android.recyclerview.adapter.MutableListAdapter

class SpotlightListAdapter(context: Context) : MutableListAdapter<SpotlightItem, BaseViewHolder<SpotlightItem>>(context) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getListItemAt(position)) {
            is SpotlightPromoItem -> VIEW_TYPE_PROMO
            is SpotlightReleaseItem -> VIEW_TYPE_RELEASE
            else -> throw IllegalArgumentException("Unsupported item at position $position: $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SpotlightItem> {
        return when (viewType) {
            VIEW_TYPE_PROMO -> SpotlightPromoViewHolder(
                context,
                parent
            )
            VIEW_TYPE_RELEASE -> SpotlightReleaseViewHolder(
                context,
                parent
            )
            else -> throw IllegalArgumentException("Unsupported viewType: $viewType")
        } as BaseViewHolder<SpotlightItem>
    }

    companion object {
        private const val VIEW_TYPE_PROMO = 0
        private const val VIEW_TYPE_RELEASE = 1
    }
}