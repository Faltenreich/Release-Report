package com.faltenreich.release.ui.list.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.faltenreich.release.ui.list.adapter.ReleaseListAdapter

class ReleaseListLayoutManager(
    context: Context,
    private val listAdapter: ReleaseListAdapter?
) : GridLayoutManager(context, SPAN_COUNT) {

    init {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return listAdapter?.takeIf { adapter -> adapter.itemCount > 0 }?.let { adapter ->
                    return@let when (adapter.getItemViewType(position)) {
                        ReleaseListAdapter.VIEW_TYPE_DATE -> SPAN_COUNT
                        else -> 1
                    }
                } ?: 1
            }
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}