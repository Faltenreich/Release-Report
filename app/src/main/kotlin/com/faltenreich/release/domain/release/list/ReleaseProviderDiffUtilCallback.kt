package com.faltenreich.release.domain.release.list

import androidx.recyclerview.widget.DiffUtil

class ReleaseProviderDiffUtilCallback : DiffUtil.ItemCallback<ReleaseProvider>() {

    override fun areItemsTheSame(oldItem: ReleaseProvider, newItem: ReleaseProvider): Boolean {
        return oldItem::class == newItem::class && oldItem.release == newItem.release
    }

    override fun areContentsTheSame(oldItem: ReleaseProvider, newItem: ReleaseProvider): Boolean {
        return oldItem == newItem
    }
}