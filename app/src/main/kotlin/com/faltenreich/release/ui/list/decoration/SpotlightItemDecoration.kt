package com.faltenreich.release.ui.list.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.extension.isFalse
import com.faltenreich.release.ui.list.adapter.SpotlightListAdapter
import com.faltenreich.release.ui.list.item.SpotlightItem

class SpotlightItemDecoration(context: Context) : GridLayoutItemDecoration<SpotlightItem, SpotlightListAdapter>() {
    private val spacing: Int = context.resources.getDimension(SPACING_RES_DEFAULT).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (layoutManager?.isMeasurementCacheEnabled.isFalse) {
            layoutManager?.isMeasurementCacheEnabled = true
        }

        val position = parent.getChildAdapterPosition(view)
        val spanSize = layoutManager?.spanSizeLookup?.getSpanSize(position) ?: 1

        outRect.bottom = spacing
        if (spanSize == 1) {
            val column = layoutManager?.spanSizeLookup?.getSpanIndex(position, spanCount) ?: 0
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
        }
    }
}