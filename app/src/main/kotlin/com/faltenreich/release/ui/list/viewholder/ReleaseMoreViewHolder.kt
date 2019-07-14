package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.ui.list.item.ReleaseMoreItem
import kotlinx.android.synthetic.main.list_item_release_more.*

class ReleaseMoreViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<ReleaseMoreItem>(context, R.layout.list_item_release_more, parent) {

    override fun onBind(data: ReleaseMoreItem) {
        moreTextView.text = "${data.count}+"
    }
}