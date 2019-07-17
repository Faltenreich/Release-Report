package com.faltenreich.release.ui.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class GridLayoutItemDecoration : RecyclerView.ItemDecoration() {
    protected lateinit var layoutManager: GridLayoutManager

    protected val spanCount: Int
        get() = layoutManager.spanCount

    @CallSuper
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (!::layoutManager.isInitialized) {
            layoutManager = parent.layoutManager as GridLayoutManager
        }
    }
}