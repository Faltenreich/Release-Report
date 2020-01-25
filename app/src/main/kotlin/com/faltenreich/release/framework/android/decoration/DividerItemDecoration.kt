package com.faltenreich.release.framework.android.decoration

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R

class DividerItemDecoration(context: Context) : DividerItemDecoration(context, RecyclerView.VERTICAL) {

    init {
        ContextCompat.getDrawable(context, R.drawable.separator_gray)?.let { drawable -> setDrawable(drawable) }
    }
}