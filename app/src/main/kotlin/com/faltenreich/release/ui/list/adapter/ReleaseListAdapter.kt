package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.DateItem
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.list.item.ReleaseMoreItem
import com.faltenreich.release.ui.list.pagination.ReleaseItemDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.BaseViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseDateViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseImageViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseMoreViewHolder
import org.threeten.bp.LocalDate

class ReleaseListAdapter(context: Context) : PagedListAdapter<DateItem, BaseViewHolder<DateItem>>(context, ReleaseItemDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getListItemAt(position)) {
            is ReleaseDateItem -> VIEW_TYPE_DATE
            is ReleaseItem -> VIEW_TYPE_RELEASE
            is ReleaseMoreItem -> VIEW_TYPE_MORE
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DateItem> {
        return when (viewType) {
            VIEW_TYPE_DATE -> ReleaseDateViewHolder(context, parent)
            VIEW_TYPE_RELEASE -> ReleaseImageViewHolder(context, parent)
            VIEW_TYPE_MORE -> ReleaseMoreViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        } as BaseViewHolder<DateItem>
    }

    fun getFirstPositionForDate(date: LocalDate): Int? {
        return listItems.indexOfFirst { item -> item.date.isEqual(date) }.takeIf { index -> index >= 0 }
    }

    companion object {
        const val VIEW_TYPE_DATE = 0
        const val VIEW_TYPE_RELEASE = 1
        const val VIEW_TYPE_MORE = 2
    }
}