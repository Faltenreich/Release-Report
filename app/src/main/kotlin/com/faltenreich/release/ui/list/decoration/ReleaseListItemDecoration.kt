package com.faltenreich.release.ui.list.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.ui.list.adapter.ReleaseListAdapter
import com.faltenreich.release.ui.list.item.ReleaseDateItem
import com.faltenreich.release.ui.list.item.ReleaseItem
import com.faltenreich.release.ui.logic.provider.DateProvider

class ReleaseListItemDecoration(
    context: Context,
    private val spacing: Int
) : LinearLayoutItemDecoration<DateProvider, ReleaseListAdapter>() {
    private val divider: Int = context.resources.getDimension(R.dimen.margin_padding_size_xxxxsmall).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        when (adapter?.getListItemAt(position)) {
            is ReleaseDateItem -> {
                outRect.top = spacing
                outRect.bottom = spacing
            }
            is ReleaseItem -> {
                outRect.left = spacing
                outRect.right = spacing
                outRect.bottom = divider
            }
        }
    }
}