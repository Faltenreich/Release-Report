package com.faltenreich.release.ui.list.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R

class GridDividerDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val divider: Drawable? = ContextCompat.getDrawable(context, R.drawable.separator)

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val top = parent.paddingTop
        val right = parent.width - parent.paddingRight
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (index in 0 until childCount) {
            val child = parent.getChildAt(index)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val childTop = child.bottom + params.bottomMargin
            val childBottom = childTop + divider!!.intrinsicHeight

            divider.setBounds(left, childTop, right, childBottom)
            divider.draw(canvas)

            val childLeft = child.left + params.leftMargin
            val childRight = childLeft + divider.intrinsicWidth

            divider.setBounds(childLeft, top, childRight, bottom)
            divider.draw(canvas)
        }
    }
}