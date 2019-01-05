package com.faltenreich.releaseradar.extension

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator

val Context.screenSize: Point
    get() = Point().also { point ->
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(point)
    }

fun Context.actionBarSize(): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
    return TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
}

fun View.animateHeight(to: Int) {
    AnimatorSet().apply {
        play(ValueAnimator.ofInt(height, to).apply {
            duration = 500
            addUpdateListener { animation ->
                layoutParams.height = animation.animatedValue as Int
                requestLayout()
            }
        })
        interpolator = AccelerateDecelerateInterpolator()
    }.start()
}
