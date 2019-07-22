package com.faltenreich.release.ui.list.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.ui.list.adapter.DiscoverListAdapter
import com.faltenreich.release.ui.logic.provider.DateProvider

class DiscoverItemDecoration(context: Context) : GridLayoutItemDecoration<DateProvider, DiscoverListAdapter>() {
    private val spacing: Int = context.resources.getDimension(SPACING_RES_DEFAULT).toInt()
    private val stickyHeaderHeight: Int = context.resources.getDimensionPixelSize(R.dimen.toolbar_height)

    var isSkeleton: Boolean = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val position = layoutParams.viewAdapterPosition
        val column = layoutParams.spanIndex

        outRect.top = if (isSkeleton && position < spanCount) stickyHeaderHeight + spacing else 0
        outRect.bottom = spacing
        if (layoutParams.spanSize == 1) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
        }
    }
}
