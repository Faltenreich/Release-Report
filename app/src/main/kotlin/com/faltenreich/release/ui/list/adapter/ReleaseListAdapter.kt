package com.faltenreich.release.ui.list.adapter

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.ui.list.item.ListDateItem
import com.faltenreich.release.ui.list.item.ListItem
import com.faltenreich.release.ui.list.pagination.ListDiffUtilCallback
import com.faltenreich.release.ui.list.viewholder.BaseViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseDateViewHolder
import com.faltenreich.release.ui.list.viewholder.ReleaseImageViewHolder
import org.threeten.bp.LocalDate

class ReleaseListAdapter(context: Context) : PagedListAdapter<ListItem, BaseViewHolder<ListItem>>(context, ListDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int = when {
        position < itemCount && getListItemAt(position) is ListDateItem -> VIEW_TYPE_DATE
        else -> VIEW_TYPE_RELEASE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListItem> {
        return when (viewType) {
            VIEW_TYPE_RELEASE -> ReleaseImageViewHolder(context, parent)
            VIEW_TYPE_DATE -> ReleaseDateViewHolder(context, parent)
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        } as BaseViewHolder<ListItem>
    }

    fun getFirstPositionForDate(date: LocalDate): Int? {
        return listItems.indexOfFirst { item -> item.date.isEqual(date) }.takeIf { index -> index >= 0 }
    }

    companion object {
        const val VIEW_TYPE_RELEASE = 0
        const val VIEW_TYPE_DATE = 1
    }
}