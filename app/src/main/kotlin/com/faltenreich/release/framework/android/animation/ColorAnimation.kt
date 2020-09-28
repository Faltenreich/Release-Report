package com.faltenreich.release.framework.android.animation

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import com.faltenreich.release.framework.android.animation.provider.ColorProvider

class ColorAnimation(
    private val provider: ColorProvider,
    private val targetColor: Int,
    private val durationInMillis: Long =
        provider.context.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
) {

    fun start() {
        val currentColor = provider.color
        val shouldChange = currentColor == null || currentColor != targetColor
        if (shouldChange) {
            val shouldAnimate = provider.shouldAnimate && currentColor != null
            if (shouldAnimate) {
                ValueAnimator.ofObject(ArgbEvaluator(), currentColor, targetColor).apply {
                    duration = durationInMillis
                    addUpdateListener { animator -> provider.color = animator.animatedValue as Int }
                }.start()
            } else {
                provider.color = targetColor
            }
        }
    }
}