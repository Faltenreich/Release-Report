package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.ui.logic.provider.LabelProvider
import kotlinx.android.synthetic.main.list_item_header.*

class HeaderViewHolder(
    context: Context, parent: ViewGroup
) : BaseViewHolder<LabelProvider>(context, R.layout.list_item_header, parent) {
    override fun onBind(data: LabelProvider) {
        labelTextView.text = data.print(context)
    }
}