package com.faltenreich.releaseradar.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.ui.list.paging.ReleaseListItemDiffUtilCallback
import com.faltenreich.releaseradar.ui.list.viewholder.BaseViewHolder
import com.faltenreich.releaseradar.ui.list.viewholder.ReleaseDateViewHolder
import com.faltenreich.releaseradar.ui.list.viewholder.ReleaseItemViewHolder
import org.threeten.bp.LocalDate

class ReleaseListAdapter(context: Context) : PagedListAdapter<ReleaseListItem, BaseViewHolder<ReleaseListItem>>(context, ReleaseListItemDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int = when {
        position < itemCount && getListItemAt(position)?.release == null -> VIEW_TYPE_DATE
        else -> VIEW_TYPE_RELEASE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_RELEASE -> ReleaseItemViewHolder(context, parent)
        VIEW_TYPE_DATE -> ReleaseDateViewHolder(context, parent)
        else -> throw IllegalArgumentException("Unknown viewType: $viewType")
    }

    fun getFirstPositionForDate(date: LocalDate): Int? = listItems.indexOfFirst { item -> item.date?.isEqual(date) ?: false }.takeIf { index -> index >= 0 }

    companion object {
        const val VIEW_TYPE_RELEASE = 0
        const val VIEW_TYPE_DATE = 1
    }
}