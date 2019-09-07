package com.faltenreich.release.framework.android.view

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
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