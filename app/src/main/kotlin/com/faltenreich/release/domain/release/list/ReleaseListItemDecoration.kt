package com.faltenreich.release.domain.release.list

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.view.recyclerview.decoration.LinearLayoutItemDecoration

class ReleaseListItemDecoration(
    context: Context,
    private val spacing: Int
) : LinearLayoutItemDecoration() {

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