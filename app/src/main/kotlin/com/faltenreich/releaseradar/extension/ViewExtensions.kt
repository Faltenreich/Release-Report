package com.faltenreich.releaseradar.extension

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.animation.ArgbEvaluatorCompat

private const val COLOR_FADE_ANIMATION_DURATION = 300

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

val View.backgroundColor: Int
    get() = (background as ColorDrawable).color

fun View.fadeBackgroundColorResource(@ColorRes to: Int) {
    val colorFrom = backgroundColor
    val colorTo = ContextCompat.getColor(context, to)
    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluatorCompat(), colorFrom, colorTo)
    colorAnimation.addUpdateListener { animator -> setBackgroundColor(animator.animatedValue as Int) }
    colorAnimation.duration = COLOR_FADE_ANIMATION_DURATION.toLong()
    colorAnimation.start()
}