package com.faltenreich.release.framework.android.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.domain.release.list.ReleaseProvider
import com.faltenreich.release.domain.release.spotlight.SpotlightContainerListAdapter

class GridLayoutSpacingItemDecoration(context: Context) : GridLayoutItemDecoration<ReleaseProvider, SpotlightContainerListAdapter>() {

    private val spacing: Int = context.resources.getDimension(SPACING_RES_DEFAULT).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val position = layoutParams.viewAdapterPosition
        val column = layoutParams.spanIndex

        outRect.top = if (position < spanCount) spacing else 0
        outRect.bottom = spacing
        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount
    }
}
