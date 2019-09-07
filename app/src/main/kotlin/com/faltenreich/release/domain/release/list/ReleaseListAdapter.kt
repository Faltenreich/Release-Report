package com.faltenreich.release.domain.release.list

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.domain.date.DateProviderDiffUtilCallback
import com.faltenreich.release.framework.android.viewholder.BaseViewHolder
import com.faltenreich.release.framework.android.adapter.PagedListAdapter
import com.faltenreich.release.domain.date.DateProvider
import org.threeten.bp.LocalDate

class ReleaseListAdapter(context: Context) : PagedListAdapter<DateProvider, BaseViewHolder<DateProvider>>(context,
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
                showButton = false
            )
            VIEW_TYPE_RELEASE -> ReleaseDetailViewHolder(
                context,
                parent,
                showDate = false
            )
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        } as BaseViewHolder<DateProvider>
    }

    fun getFirstPositionForDate(date: LocalDate): Int? {
        return listItems.indexOfFirst { item -> item.date.isEqual(date) }.takeIf { index -> index >= 0 }
    }

    companion object {
        const val VIEW_TYPE_DATE = 0
        const val VIEW_TYPE_RELEASE = 1
    }
}