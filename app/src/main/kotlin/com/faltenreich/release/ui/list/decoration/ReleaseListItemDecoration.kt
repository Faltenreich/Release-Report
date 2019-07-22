package com.faltenreich.release.ui.list.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.ui.list.adapter.ReleaseListAdapter
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.logic.provider.DateProvider

class ReleaseListItemDecoration(
    context: Context
) : LinearLayoutItemDecoration<DateProvider, ReleaseListAdapter>() {
    private val spacing: Int = context.resources.getDimension(SPACING_RES_DEFAULT).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        when (adapter?.getListItemAt(position)) {
            is ReleaseItem -> {
                outRect.left = spacing
                outRect.right = spacing
            }
        }
        outRect.bottom = spacing
    }
}