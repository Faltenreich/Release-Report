package com.faltenreich.releaseradar.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.faltenreich.releaseradar.ui.list.adapter.ReleaseListItem

abstract class ReleaseViewHolder(context: Context, @LayoutRes layoutResId: Int, parent: ViewGroup) : BaseViewHolder<ReleaseListItem>(context, layoutResId, parent)