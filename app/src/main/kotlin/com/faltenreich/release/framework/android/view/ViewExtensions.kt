package com.faltenreich.release.framework.android.view

import android.app.Activity
import android.content.ContextWrapper
import android.content.res.ColorStateList
import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.paging.PagedList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


var View.backgroundTint: Int
    get() = throw UnsupportedOperationException()
    set(value) {
        backgroundTintList = ColorStateList.valueOf(value)
    }

var ImageView.tint: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { imageTintList = ColorStateList.valueOf(value) }

var ImageView.tintResource: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { tint = ContextCompat.getColor(context, value) }

var FloatingActionButton.backgroundTint: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { backgroundTintList = ColorStateList.valueOf(value) }

var FloatingActionButton.backgroundTintResource: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { backgroundTint = ContextCompat.getColor(context, value) }

var FloatingActionButton.foregroundTint: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) = setColorFilter(value)

var FloatingActionButton.foregroundTintResource: Int
    get() = throw java.lang.UnsupportedOperationException()
    set(value) { foregroundTint = ContextCompat.getColor(context, value) }

fun <T> PagedList<T>.onInserted(onInserted: (position: Int, count: Int) -> Unit) {
    addWeakCallback(this, object : PagedList.Callback() {
        override fun onInserted(position: Int, count: Int) {
            onInserted(position, count)
            removeWeakCallback(this)
        }
        override fun onChanged(position: Int, count: Int) = Unit
        override fun onRemoved(position: Int, count: Int) = Unit
    })
}

fun View.showSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).show()
}

fun View.showSnackbar(@StringRes textRes: Int) {
    showSnackbar(context.getString(textRes))
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

fun Toolbar.fitSystemWindows() {
    // Workaround: Fixing fitsSystemWindows programmatically
    doOnPreDraw {
        val frame = Rect()
        activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
        layoutParams.height = height + frame.top
        setPadding(0, frame.top, 0, 0)
    }
}