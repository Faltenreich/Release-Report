package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.releaseradar.R

class ReleaseListItemDecoration(
    private val context: Context,
    @DimenRes paddingResId: Int,
    private val spanCount: Int,
    @LayoutRes private val stickyHeaderResId: Int,
    @IdRes private val stickyHeaderLabelResId: Int,
    private val getStickyHeaderLabel: () -> String?
) : RecyclerView.ItemDecoration() {

    private val padding: Int by lazy { context.resources.getDimension(paddingResId).toInt() }
    private var stickyHeader: View? = null
    private var stickyHeaderLabel: TextView? = null
    private val stickyHeaderHeight: Int
        get() = context.resources.getDimensionPixelSize(R.dimen.toolbar_height)

    var isSkeleton: Boolean = false

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val position = layoutParams.viewAdapterPosition
        val column = layoutParams.spanIndex

        outRect.top = if (isSkeleton && position < spanCount) stickyHeaderHeight + padding else 0
        outRect.bottom = padding
        if (layoutParams.spanSize == 1) {
            outRect.left = padding - column * padding / spanCount
            outRect.right = (column + 1) * padding / spanCount
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)

        if (stickyHeader == null) {
            stickyHeader = LayoutInflater.from(parent.context).inflate(stickyHeaderResId, parent, false).apply {
                fixLayoutSize(this, parent)
                stickyHeaderLabel = findViewById(stickyHeaderLabelResId)
            }
        }
        stickyHeaderLabel?.text = getStickyHeaderLabel()
        canvas.save()
        canvas.translate(0f, 0f)
        stickyHeader?.draw(canvas)
        canvas.restore()
    }

    /**
     * Measures the header view to make sure its size is greater than 0 and will be drawn
     * https://yoda.entelect.co.za/view/9627/how-to-android-recyclerview-item-decorations
     */
    private fun fixLayoutSize(view: View, parent: ViewGroup) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        val childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)

        view.measure(childWidth, childHeight)

        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }
}
