package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class VerticalPaddingItemDecoration(context: Context, @DimenRes paddingResId: Int) : RecyclerView.ItemDecoration() {

    private val padding by lazy { context.resources.getDimension(paddingResId).toInt() }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val count = parent.adapter?.itemCount ?:0
        outRect.bottom = when (position) {
            RecyclerView.NO_POSITION -> 0
            count - 1 -> 0
            else -> padding
        }
    }
}
