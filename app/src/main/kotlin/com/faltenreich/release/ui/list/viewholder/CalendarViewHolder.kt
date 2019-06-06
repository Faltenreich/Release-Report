package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.faltenreich.release.ui.list.item.CalendarListItem

abstract class CalendarViewHolder<T : CalendarListItem>(
    context: Context,
    @LayoutRes layoutRes: Int,
    parent: ViewGroup
) : BaseViewHolder<T>(context, layoutRes, parent)