package com.faltenreich.release.ui.list.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R

class ReleaseListItemDecoration(context: Context) : GridLayoutItemDecoration() {
    private val padding: Int = context.resources.getDimension(R.dimen.margin_padding_size_small).toInt()
    private val stickyHeaderHeight: Int = context.resources.getDimensionPixelSize(R.dimen.toolbar_height)

    var isSkeleton: Boolean = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

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
