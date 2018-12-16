package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class SpaceOnEachSideItemDecoration(context: Context, @DimenRes spaceResId: Int) : RecyclerView.ItemDecoration() {

    private val padding by lazy { context.resources.getDimension(spaceResId).toInt() }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        when (position) {
            RecyclerView.NO_POSITION -> outRect.set(0, 0, 0, 0)
            else -> outRect.set(padding, padding, padding, padding)
        }
    }
}
