package com.faltenreich.release.framework.android.recyclerview.decoration

import androidx.recyclerview.widget.GridLayoutManager

abstract class GridLayoutItemDecoration : ItemDecoration<GridLayoutManager>() {

    protected val spanCount: Int
        get() = layoutManager?.spanCount ?: 1
}