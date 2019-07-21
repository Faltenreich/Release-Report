package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseEmptyItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.list.pagination.DateProviderDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.BaseViewHolder
import com.faltenreich.release.ui.list.viewholder.HeaderViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseDetailViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseEmptyViewHolder
import com.faltenreich.release.ui.logic.provider.DateProvider

class ReleaseListAdapter(context: Context) : PagedListAdapter<DateProvider, BaseViewHolder<DateProvider>>(context, DateProviderDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getListItemAt(position)) {
            is ReleaseDateItem -> VIEW_TYPE_DATE
            is ReleaseItem -> VIEW_TYPE_RELEASE
            is ReleaseEmptyItem -> VIEW_TYPE_EMPTY
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DateProvider> {
        return when (viewType) {
            VIEW_TYPE_DATE -> HeaderViewHolder(context, parent)
            VIEW_TYPE_RELEASE -> ReleaseDetailViewHolder(context, parent)
            VIEW_TYPE_EMPTY -> ReleaseEmptyViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        } as BaseViewHolder<DateProvider>
    }

    companion object {
        const val VIEW_TYPE_DATE = 0
        const val VIEW_TYPE_RELEASE = 1
        const val VIEW_TYPE_EMPTY = 2
    }
}