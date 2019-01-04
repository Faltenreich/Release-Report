package com.faltenreich.releaseradar

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

val Any.className: String
    get() = javaClass.simpleName

val Context.screenSize: Point
    get() = Point().also { point ->
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(point)
    }

fun Boolean?.isTrue() = this ?: false

fun Boolean?.isTrueOrNull() = this ?: true

fun Boolean?.isFalse() = this?.let { !it } ?: false

fun Boolean?.isFalseOrNull() = this?.let { !it } ?: true

// TODO: Placeholder
fun ImageView.setImageAsync(url: String, size: Int? = null, callback: ((success: Boolean) -> Unit)? = null) {
    GlideApp
        .with(this)
        .load(url)
        .apply { size?.let { override(size) } }
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                callback?.invoke(true)
                return false
            }
            override fun onLoadFailed(exception: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                callback?.invoke(false)
                return false
            }
        })
        .into(this)
}

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