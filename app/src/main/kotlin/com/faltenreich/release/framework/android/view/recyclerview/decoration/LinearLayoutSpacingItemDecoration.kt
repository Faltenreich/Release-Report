package com.faltenreich.release.framework.android.view.recyclerview.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class LinearLayoutSpacingItemDecoration(
    context: Context,
    @DimenRes spacingRes: Int = SPACING_RES_DEFAULT
) : LinearLayoutItemDecoration() {

    private val spacing: Int = context.resources.getDimension(spacingRes).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        when (parent.getChildAdapterPosition(view)) {
            0 -> return
            else -> outRect.top = spacing
        }
    }
}