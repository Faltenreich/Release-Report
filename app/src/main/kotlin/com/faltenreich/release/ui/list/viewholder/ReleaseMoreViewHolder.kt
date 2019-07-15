package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.ui.list.adapter.ReleaseMoreListAdapter
import com.faltenreich.release.ui.list.item.ReleaseMoreItem
import com.faltenreich.release.ui.list.layoutmanager.MoreLayoutManager
import kotlinx.android.synthetic.main.list_item_release_more.*
import kotlin.math.min

class ReleaseMoreViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<ReleaseMoreItem>(context, R.layout.list_item_release_more, parent) {

    override fun onBind(data: ReleaseMoreItem) {
        val releases = data.releases

        val listAdapter = ReleaseMoreListAdapter(context)
        listView.layoutManager = MoreLayoutManager(context)
        listView.adapter = listAdapter
        listAdapter.addListItems(releases.subList(0, min(MAX_COUNT_LIST_ITEMS, releases.size)))
        listAdapter.notifyDataSetChanged()

        moreTextView.text = "${releases.size}+"
    }

    companion object {
        private const val MAX_COUNT_LIST_ITEMS = 4
    }
}