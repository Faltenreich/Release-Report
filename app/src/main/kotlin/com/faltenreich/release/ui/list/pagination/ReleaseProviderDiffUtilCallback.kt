package com.faltenreich.release.ui.list.pagination

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.ui.logic.provider.ReleaseProvider

class ReleaseProviderDiffUtilCallback : DiffUtil.ItemCallback<ReleaseProvider>() {

    override fun areItemsTheSame(oldItem: ReleaseProvider, newItem: ReleaseProvider): Boolean {
        return oldItem::class == newItem::class && oldItem.release == newItem.release
    }

    override fun areContentsTheSame(oldItem: ReleaseProvider, newItem: ReleaseProvider): Boolean {
        return oldItem == newItem
    }
}