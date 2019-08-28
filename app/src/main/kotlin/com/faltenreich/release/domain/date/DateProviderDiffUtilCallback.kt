package com.faltenreich.release.domain.date

import androidx.recyclerview.widget.DiffUtil

class DateProviderDiffUtilCallback : DiffUtil.ItemCallback<DateProvider>() {

    override fun areItemsTheSame(oldItem: DateProvider, newItem: DateProvider): Boolean {
        return oldItem::class == newItem::class && oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: DateProvider, newItem: DateProvider): Boolean {
        return oldItem == newItem
    }
}