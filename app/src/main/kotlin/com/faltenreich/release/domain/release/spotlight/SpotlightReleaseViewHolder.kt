package com.faltenreich.release.domain.release.spotlight

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.domain.release.detail.ReleaseOpener
import com.faltenreich.release.framework.android.recycler.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_spotlight.*

class SpotlightReleaseViewHolder(
    context: Context,
    parent: ViewGroup
) : BaseViewHolder<SpotlightReleaseItem>(context, R.layout.list_item_spotlight, parent),
    ReleaseOpener {

    private val listAdapter = SpotlightContainerListAdapter(context)

    init {
        listView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        listView.adapter = listAdapter
    }

    override fun onBind(data: SpotlightReleaseItem) {
        titleTextView.text = data.print(context)
        listAdapter.removeListItems()
        listAdapter.addListItems(data.releases)
        listAdapter.notifyDataSetChanged()
    }
}