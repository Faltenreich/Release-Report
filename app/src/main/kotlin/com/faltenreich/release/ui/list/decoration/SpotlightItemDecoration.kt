package com.faltenreich.release.ui.list.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpotlightItemDecoration(context: Context, @DimenRes private val spacingResId: Int) : RecyclerView.ItemDecoration() {
    private val spacing: Int = context.resources.getDimension(spacingResId).toInt()
    private lateinit var layoutManager: GridLayoutManager

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (!::layoutManager.isInitialized) {
            layoutManager = parent.layoutManager as GridLayoutManager
            layoutManager.isMeasurementCacheEnabled = true
        }

        val spanCount = layoutManager.spanCount

        val position = parent.getChildAdapterPosition(view)
        val spanSize = layoutManager.spanSizeLookup?.getSpanSize(position) ?: 1

        if (spanSize == 1) {
            val column = layoutManager.spanSizeLookup.getSpanIndex(position, spanCount)
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
            outRect.top = spacing
            outRect.bottom = spacing
        }
    }
}