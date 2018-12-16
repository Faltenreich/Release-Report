package com.faltenreich.releaseradar

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import com.bumptech.glide.Glide

// TODO: Placeholder and scale
fun ImageView.setImageAsync(url: String) = Glide.with(this).load(url).into(this)

var ImageView.tint: Int
    get() = TODO()
    set(value) { imageTintList = ColorStateList.valueOf(value) }

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