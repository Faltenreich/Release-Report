package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView



class GridPaddingItemDecoration(context: Context, @DimenRes spaceResId: Int, private val spanCount: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    private val padding by lazy { context.resources.getDimension(spaceResId).toInt() }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = padding - column * padding / spanCount
            outRect.right = (column + 1) * padding / spanCount

            if (position < spanCount) {
                outRect.top = padding
            }
            outRect.bottom = padding
        } else {
            outRect.left = column * padding / spanCount
            outRect.right = padding - (column + 1) * padding / spanCount
            if (position >= spanCount) {
                outRect.top = padding
            }
        }
    }
}
