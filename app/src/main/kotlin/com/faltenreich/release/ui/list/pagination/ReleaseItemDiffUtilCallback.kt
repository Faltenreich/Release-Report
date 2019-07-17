package com.faltenreich.release.ui.list.pagination

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.release.ui.list.provider.DateProvider

class ReleaseItemDiffUtilCallback : DiffUtil.ItemCallback<DateProvider>() {

    override fun areItemsTheSame(oldItem: DateProvider, newItem: DateProvider): Boolean {
        return oldItem::class == newItem::class && oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: DateProvider, newItem: DateProvider): Boolean {
        return oldItem == newItem
    }
}