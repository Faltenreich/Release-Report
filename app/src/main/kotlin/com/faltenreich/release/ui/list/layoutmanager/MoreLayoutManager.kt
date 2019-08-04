package com.faltenreich.release.ui.list.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MoreLayoutManager(context: Context) : GridLayoutManager(context, 2) {
    private var adapter: RecyclerView.Adapter<*>? = null

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = adapter ?: return 1
                return when (position) {
                    adapter.itemCount - 1 -> 2
                    else -> 1
                }
            }
        }
    }

    override fun onAdapterChanged(oldAdapter: RecyclerView.Adapter<*>?, newAdapter: RecyclerView.Adapter<*>?) {
        super.onAdapterChanged(oldAdapter, newAdapter)
        adapter = newAdapter
    }
}