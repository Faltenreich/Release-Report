package com.faltenreich.release.framework.android.view.toolbar

import android.graphics.Rect
import androidx.core.view.doOnPreDraw
import com.faltenreich.release.framework.android.view.activity
import com.google.android.material.appbar.MaterialToolbar

fun MaterialToolbar.fitSystemWindows() {
    // Workaround: Fixing fitsSystemWindows programmatically
    doOnPreDraw {
        val frame = Rect()
        activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
        layoutParams.height = height + frame.top
        setPadding(0, frame.top, 0, 0)
    }
}