package com.faltenreich.releaseradar.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class ReleaseListItemDiffUtilCallback : DiffUtil.ItemCallback<ReleaseListItem>() {
    override fun areItemsTheSame(oldItem: ReleaseListItem, newItem: ReleaseListItem): Boolean = oldItem.date == newItem.date && oldItem.release == newItem.release
    override fun areContentsTheSame(oldItem: ReleaseListItem, newItem: ReleaseListItem): Boolean = oldItem == newItem
}