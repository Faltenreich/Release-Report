package com.faltenreich.release.domain.release.calendar

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.recycler.decoration.GridLayoutItemDecoration

class CalendarItemDecoration(context: Context) : GridLayoutItemDecoration() {

    private val color = ContextCompat.getColor(context, R.color.colorPrimary)
    private val spacing = context.resources.getDimensionPixelSize(R.dimen.margin_padding_size_xxxxsmall)

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        // TODO
    }
}