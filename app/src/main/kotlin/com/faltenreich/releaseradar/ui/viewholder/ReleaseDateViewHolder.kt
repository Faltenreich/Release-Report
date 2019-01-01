package com.faltenreich.releaseradar.ui.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.print
import com.faltenreich.releaseradar.ui.adapter.ReleaseListItem
import kotlinx.android.synthetic.main.list_item_release_date.*

class ReleaseDateViewHolder(context: Context, parent: ViewGroup) : ReleaseViewHolder(context, R.layout.list_item_release_date, parent) {

    override fun onBind(data: ReleaseListItem) {
        releaseDateTextView.text = data.date?.print()
    }
}