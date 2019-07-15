package com.faltenreich.release.ui.list.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class MoreLayoutManager(context: Context) : GridLayoutManager(context, SPAN_COUNT) {
    companion object {
        private const val SPAN_COUNT = 2
    }
}