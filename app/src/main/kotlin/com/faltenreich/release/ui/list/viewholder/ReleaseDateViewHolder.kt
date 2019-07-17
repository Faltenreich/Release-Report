package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import com.faltenreich.release.R
import com.faltenreich.release.extension.print
import com.faltenreich.release.ui.list.provider.DateProvider
import kotlinx.android.synthetic.main.list_item_release_date.*

class ReleaseDateViewHolder(context: Context, parent: ViewGroup) : BaseViewHolder<DateProvider>(context, R.layout.list_item_release_date, parent) {

    override fun onBind(data: DateProvider) {
        releaseDateTextView.text = data.date.print(context)
    }
}