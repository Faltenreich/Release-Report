package com.faltenreich.release.framework.android.view

import android.app.Activity
import android.content.ContextWrapper
import android.content.res.ColorStateList
import android.view.View

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