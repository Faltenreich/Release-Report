package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.ui.viewholder.ReleaseDateViewHolder
import com.faltenreich.releaseradar.ui.viewholder.ReleaseItemViewHolder
import com.faltenreich.releaseradar.ui.viewholder.ReleaseViewHolder

class ReleaseListAdapter(context: Context) : PagedListAdapter<ReleaseListItem, ReleaseViewHolder>(context, ReleaseListDiffUtilItemCallback()) {

    override fun getItemViewType(position: Int): Int = when {
        position < itemCount && getListItemAt(position).release == null -> VIEW_TYPE_DATE
        else -> VIEW_TYPE_RELEASE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseViewHolder = when (viewType) {
        VIEW_TYPE_RELEASE -> ReleaseItemViewHolder(context, parent)
        VIEW_TYPE_DATE -> ReleaseDateViewHolder(context, parent)
        else -> throw IllegalArgumentException("Unknown viewType: $viewType")
    }

    companion object {
        const val VIEW_TYPE_RELEASE = 0
        const val VIEW_TYPE_DATE = 1
    }
}