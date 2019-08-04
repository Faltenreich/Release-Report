package com.faltenreich.release.ui.list.viewholder

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.faltenreich.release.R
import com.faltenreich.release.ui.logic.opener.DateOpener
import com.faltenreich.release.ui.logic.provider.DateProvider
import kotlinx.android.synthetic.main.list_item_header.*

class HeaderViewHolder(
    context: Context,
    parent: ViewGroup,
    private val showButton: Boolean
) : BaseViewHolder<DateProvider>(context, R.layout.list_item_header, parent), DateOpener {
    override fun onBind(data: DateProvider) {
        headerTextView.text = data.print(context)
        headerButton.isVisible = showButton
        headerButton.setOnClickListener { openDate(navigationController, data.date) }
    }
}