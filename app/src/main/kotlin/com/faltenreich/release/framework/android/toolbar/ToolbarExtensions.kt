package com.faltenreich.release.framework.android.toolbar

import android.graphics.Rect
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnPreDraw
import com.faltenreich.release.framework.android.view.activity

fun Toolbar.fitSystemWindows() {
    // Workaround: Fixing fitsSystemWindows programmatically
    doOnPreDraw {
        val frame = Rect()
        activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
        layoutParams.height = height + frame.top
        setPadding(0, frame.top, 0, 0)
    }
}