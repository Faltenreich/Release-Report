package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.ui.list.item.DateItem

class ReleaseEmptyViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<DateItem>(context, R.layout.list_item_release_empty, parent) {
    override fun onBind(data: DateItem) = Unit
}