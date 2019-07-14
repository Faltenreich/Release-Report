package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.faltenreich.release.ui.list.item.CalendarItem

abstract class CalendarViewHolder<T : CalendarItem>(
    context: Context,
    @LayoutRes layoutRes: Int,
    parent: ViewGroup
) : BaseViewHolder<T>(context, layoutRes, parent)