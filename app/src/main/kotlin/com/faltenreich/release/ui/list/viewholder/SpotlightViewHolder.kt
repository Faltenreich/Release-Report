package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.faltenreich.release.ui.list.item.SpotlightItem

abstract class SpotlightViewHolder<T : SpotlightItem>(
    context: Context,
    @LayoutRes layoutResId: Int,
    parent: ViewGroup
) : BaseViewHolder<T>(context, layoutResId, parent)