package com.faltenreich.release.ui.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.ui.list.adapter.ListAdapter

abstract class ItemDecoration<ITEM : Any, ADAPTER : ListAdapter<ITEM>, LAYOUT : RecyclerView.LayoutManager> : RecyclerView.ItemDecoration() {
    protected var adapter: ADAPTER? = null
    protected var layoutManager: LAYOUT? = null

    @CallSuper
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (adapter == null) {
            adapter = parent.adapter as? ADAPTER
        }
        if (layoutManager == null) {
            layoutManager = parent.layoutManager as? LAYOUT
        }
    }

    companion object {
        const val SPACING_RES_DEFAULT = R.dimen.margin_padding_size_small
    }
}