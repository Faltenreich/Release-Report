package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.DateItem
import com.faltenreich.release.ui.list.pagination.ReleaseListItemDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.BaseViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseDateViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseImageViewHolder
import org.threeten.bp.LocalDate

class ReleaseListAdapter(context: Context) : PagedListAdapter<DateItem, BaseViewHolder<DateItem>>(context, ReleaseListItemDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int = when {
        position < itemCount && getListItemAt(position) is ReleaseDateItem -> VIEW_TYPE_DATE
        else -> VIEW_TYPE_RELEASE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DateItem> {
        return when (viewType) {
            VIEW_TYPE_RELEASE -> ReleaseImageViewHolder(context, parent)
            VIEW_TYPE_DATE -> ReleaseDateViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        } as BaseViewHolder<DateItem>
    }

    fun getFirstPositionForDate(date: LocalDate): Int? {
        return listItems.indexOfFirst { item -> item.date.isEqual(date) }.takeIf { index -> index >= 0 }
    }

    companion object {
        const val VIEW_TYPE_RELEASE = 0
        const val VIEW_TYPE_DATE = 1
    }
}