package com.faltenreich.release.framework.android.decoration

import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.framework.android.adapter.ListAdapter

abstract class GridLayoutItemDecoration<ITEM : Any, ADAPTER : ListAdapter<ITEM>> : ItemDecoration<ITEM, ADAPTER, GridLayoutManager>() {
    protected val spanCount: Int
        get() = layoutManager?.spanCount ?: 1
}