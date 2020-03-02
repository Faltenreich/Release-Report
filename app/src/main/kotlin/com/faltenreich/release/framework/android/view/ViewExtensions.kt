package com.faltenreich.release.framework.android.view

import android.app.Activity
import android.content.ContextWrapper
import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

var View.backgroundTint: Int
    get() = throw UnsupportedOperationException()
    set(value) {
        backgroundTintList = ColorStateList.valueOf(value)
    }

fun View.showSnackbar(text: String, anchor: View? = null) {
    val snackbar = Snackbar.make(this, text, Snackbar.LENGTH_LONG)
    snackbar.anchorView = anchor
    snackbar.show()
}

fun View.showSnackbar(@StringRes textRes: Int, anchor: View? = null) {
    showSnackbar(context.getString(textRes), anchor)
}

val View.activity: Activity?
    get() {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }