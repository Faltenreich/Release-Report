package com.faltenreich.release.framework.android.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.InputMethodManager

var View.backgroundTint: Int?
    get() = backgroundTintList?.defaultColor
    set(value) { backgroundTintList = value?.let { ColorStateList.valueOf(value) } }

var View.foregroundTint: Int?
    get() = foregroundTintList?.defaultColor
    set(value) { foregroundTintList = value?.let { ColorStateList.valueOf(value) } }

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

fun View.showKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}