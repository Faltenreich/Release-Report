package com.faltenreich.release.framework.android.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridLayoutSpacingItemDecoration(context: Context) : GridLayoutItemDecoration() {

    private val spacing: Int = context.resources.getDimension(SPACING_RES_DEFAULT).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val position = layoutParams.viewAdapterPosition
        val column = layoutParams.spanIndex

        outRect.left = column * spacing / spanCount
        outRect.right = spacing - (column + 1) * spacing / spanCount
        if (position >= spanCount) {
            outRect.top = spacing
        }
    }
}
