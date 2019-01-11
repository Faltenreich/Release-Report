package com.faltenreich.releaseradar.ui.list.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class HorizontalPaddingDecoration(
    context: Context,
    @DimenRes paddingResId: Int
) : RecyclerView.ItemDecoration() {

    private val padding: Int = context.resources.getDimension(paddingResId).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutParams = view.layoutParams as RecyclerView.LayoutParams
        val position = layoutParams.viewAdapterPosition
        outRect.left = if (position > 0) padding else 0
        outRect.right = padding
    }
}
