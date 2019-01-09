package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.R

class ReleaseListItemDecoration(
    context: Context,
    @DimenRes paddingResId: Int,
    private val spanCount: Int
) : RecyclerView.ItemDecoration() {

    private val stickyHeaderHeight: Int = context.resources.getDimensionPixelSize(R.dimen.toolbar_height)
    private val padding: Int = context.resources.getDimension(paddingResId).toInt()

    var isSkeleton: Boolean = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val position = layoutParams.viewAdapterPosition
        val column = layoutParams.spanIndex

        outRect.top = if (isSkeleton && position < spanCount) stickyHeaderHeight + padding else 0
        outRect.bottom = padding
        if (layoutParams.spanSize == 1) {
            outRect.left = padding - column * padding / spanCount
            outRect.right = (column + 1) * padding / spanCount
        }
    }
}
