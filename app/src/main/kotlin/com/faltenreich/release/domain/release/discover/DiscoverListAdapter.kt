package com.faltenreich.release.domain.release.discover

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.domain.date.DateProvider
import com.faltenreich.release.domain.date.DateProviderDiffUtilCallback
import com.faltenreich.release.domain.release.list.HeaderViewHolder
import com.faltenreich.release.domain.release.list.ReleaseDateItem
import com.faltenreich.release.domain.release.list.ReleaseItem
import com.faltenreich.release.framework.android.recyclerview.adapter.PagedListAdapter
import com.faltenreich.release.framework.android.recyclerview.viewholder.BaseViewHolder

class DiscoverListAdapter(
    context: Context
) : PagedListAdapter<DateProvider, BaseViewHolder<DateProvider>>(
    context,
    DateProviderDiffUtilCallback()
) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getListItemAt(position)) {
            is ReleaseDateItem -> VIEW_TYPE_DATE
            is ReleaseItem -> VIEW_TYPE_RELEASE
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DateProvider> {
        return when (viewType) {
            VIEW_TYPE_DATE -> HeaderViewHolder(
                context,
                parent,
                showButton = true
            )
            VIEW_TYPE_RELEASE -> DiscoverViewHolder(
                context,
                parent
            )
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        } as BaseViewHolder<DateProvider>
    }

    companion object {
        const val VIEW_TYPE_DATE = 0
        const val VIEW_TYPE_RELEASE = 1
    }
}