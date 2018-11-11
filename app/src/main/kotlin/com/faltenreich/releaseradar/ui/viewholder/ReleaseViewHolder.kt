package com.faltenreich.releaseradar.ui.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.releaseradar.R
import com.faltenreich.releaseradar.data.model.Release
import kotlinx.android.synthetic.main.list_item_release.*

class ReleaseViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<Release>(context, R.layout.list_item_release, parent) {
    override fun onBind(data: Release) {
        release_name.text = data.name
        release_description.text = data.description
    }
}