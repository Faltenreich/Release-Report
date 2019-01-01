package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.releaseradar.ui.viewholder.ReleaseDateViewHolder
import com.faltenreich.releaseradar.ui.viewholder.ReleaseItemViewHolder
import com.faltenreich.releaseradar.ui.viewholder.ReleaseViewHolder

class ReleaseListAdapter(context: Context) : PagedListAdapter<ReleaseListItem, ReleaseViewHolder>(context, ReleaseListDiffUtilItemCallback()) {

    override fun getItemViewType(position: Int): Int = when {
        position < itemCount && getItem(position)?.release == null -> VIEW_TYPE_DATE
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

    private class ReleaseListDiffUtilItemCallback : DiffUtil.ItemCallback<ReleaseListItem>() {
        override fun areItemsTheSame(oldItem: ReleaseListItem, newItem: ReleaseListItem): Boolean = oldItem.date == newItem.date && oldItem.release == newItem.release
        override fun areContentsTheSame(oldItem: ReleaseListItem, newItem: ReleaseListItem): Boolean = oldItem == newItem
    }
}