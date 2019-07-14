package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.ReleaseDayListItem
import com.faltenreich.release.ui.list.item.ReleaseListItem
import com.faltenreich.release.ui.list.pagination.ReleaseListItemDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.BaseViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseDateViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseImageViewHolder
import org.threeten.bp.LocalDate

class ReleaseListAdapter(context: Context) : PagedListAdapter<ReleaseListItem, BaseViewHolder<ReleaseListItem>>(context, ReleaseListItemDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int = when {
        position < itemCount && getListItemAt(position) is ReleaseDayListItem -> VIEW_TYPE_DATE
        else -> VIEW_TYPE_RELEASE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ReleaseListItem> {
        return when (viewType) {
            VIEW_TYPE_RELEASE -> ReleaseImageViewHolder(context, parent)
            VIEW_TYPE_DATE -> ReleaseDateViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        } as BaseViewHolder<ReleaseListItem>
    }

    fun getFirstPositionForDate(date: LocalDate): Int? {
        return listItems.indexOfFirst { item -> item.date.isEqual(date) }.takeIf { index -> index >= 0 }
    }

    companion object {
        const val VIEW_TYPE_RELEASE = 0
        const val VIEW_TYPE_DATE = 1
    }
}