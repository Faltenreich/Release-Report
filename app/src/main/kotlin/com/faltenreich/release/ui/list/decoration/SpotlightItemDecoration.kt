package com.faltenreich.release.ui.list.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.ui.list.adapter.SpotlightListAdapter
import com.faltenreich.release.ui.list.item.SpotlightItem

class SpotlightItemDecoration(context: Context) : LinearLayoutItemDecoration<SpotlightItem, SpotlightListAdapter>() {
    private val spacingHorizontal: Int = context.resources.getDimension(SPACING_RES_DEFAULT).toInt()
    private val spacingVertical: Int = context.resources.getDimension(R.dimen.margin_padding_size_medium).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.top = spacingVertical
        }
        outRect.left = spacingHorizontal
        outRect.right = spacingHorizontal
        outRect.bottom = spacingVertical
    }
}