package com.faltenreich.release.domain.release.discover

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class DiscoverLayoutManager(
    context: Context,
    private val listAdapter: DiscoverListAdapter?
) : GridLayoutManager(context,
    SPAN_COUNT
) {

    init {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return listAdapter?.takeIf { adapter -> adapter.itemCount > 0 }?.let { adapter ->
                    return@let when (adapter.getItemViewType(position)) {
                        DiscoverListAdapter.VIEW_TYPE_DATE -> SPAN_COUNT
                        else -> 1
                    }
                } ?: 1
            }
        }
    }

    companion object {
        private const val SPAN_COUNT = 3
    }
}