package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseEmptyItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.list.item.ReleaseMoreItem
import com.faltenreich.release.ui.list.pagination.ReleaseItemDiffUtilCallback
import com.faltenreich.release.ui.logic.provider.DateProvider
import com.faltenreich.release.ui.list.viewholder.*
import org.threeten.bp.LocalDate

class DiscoverListAdapter(context: Context) : PagedListAdapter<DateProvider, BaseViewHolder<DateProvider>>(context, ReleaseItemDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getListItemAt(position)) {
            is ReleaseDateItem -> VIEW_TYPE_DATE
            is ReleaseItem -> VIEW_TYPE_RELEASE
            is ReleaseMoreItem -> VIEW_TYPE_MORE
            is ReleaseEmptyItem -> VIEW_TYPE_EMPTY
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DateProvider> {
        return when (viewType) {
            VIEW_TYPE_DATE -> HeaderViewHolder(context, parent)
            VIEW_TYPE_RELEASE -> ReleaseImageViewHolder(context, parent)
            VIEW_TYPE_MORE -> ReleaseMoreViewHolder(context, parent)
            VIEW_TYPE_EMPTY -> ReleaseEmptyViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        } as BaseViewHolder<DateProvider>
    }

    fun getFirstPositionForDate(date: LocalDate): Int? {
        return listItems.indexOfFirst { item -> item.date.isEqual(date) }.takeIf { index -> index >= 0 }
    }

    companion object {
        const val VIEW_TYPE_DATE = 0
        const val VIEW_TYPE_RELEASE = 1
        const val VIEW_TYPE_MORE = 2
        const val VIEW_TYPE_EMPTY = 3
    }
}