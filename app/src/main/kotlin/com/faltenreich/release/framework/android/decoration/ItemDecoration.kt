package com.faltenreich.release.framework.android.decoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.adapter.ListAdapter

abstract class ItemDecoration<LAYOUT : RecyclerView.LayoutManager> : RecyclerView.ItemDecoration() {

    protected var adapter: ListAdapter<*>? = null
    protected var layoutManager: LAYOUT? = null

    @CallSuper
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (adapter == null) {
            adapter = parent.adapter as? ListAdapter<*>
        }
        if (layoutManager == null) {
            layoutManager = parent.layoutManager as LAYOUT
        }
    }

    companion object {
        const val SPACING_RES_DEFAULT = R.dimen.margin_padding_size_small
    }
}