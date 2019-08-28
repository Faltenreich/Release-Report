package com.faltenreich.release.framework.android.activity

import android.app.Activity
import android.view.View
import com.faltenreich.release.framework.android.context.hideKeyboard

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}