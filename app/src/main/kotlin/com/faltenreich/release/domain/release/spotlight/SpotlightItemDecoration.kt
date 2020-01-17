package com.faltenreich.release.domain.release.spotlight

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.framework.android.decoration.LinearLayoutItemDecoration

class SpotlightItemDecoration(context: Context) : LinearLayoutItemDecoration() {

    private val spacing: Int = context.resources.getDimension(SPACING_RES_DEFAULT).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val item = adapter?.getListItemAt(position)

        if (item is SpotlightReleaseItem) {
            if (position == 0) {
                outRect.top = spacing
            }
            outRect.left = spacing
            outRect.right = spacing
        }
        outRect.bottom = spacing
    }
}