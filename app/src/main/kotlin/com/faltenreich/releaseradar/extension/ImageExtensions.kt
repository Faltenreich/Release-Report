package com.faltenreich.releaseradar.extension

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.floatingactionbutton.FloatingActionButton

@GlideModule
class ImageLoadingModule : AppGlideModule()

fun ImageView.setImageAsync(url: String, size: Int? = null, callback: ((success: Boolean) -> Unit)? = null) {
    GlideApp
        .with(this)
        .load(url)
        .apply { size?.let { override(size) } }
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
            ): Boolean {
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

var ImageView.tintResource: Int
    get() = TODO()
    set(value) { tint = ContextCompat.getColor(context, value) }

var FloatingActionButton.backgroundTint: Int
    get() = TODO()
    set(value) { backgroundTintList = ColorStateList.valueOf(value) }

var FloatingActionButton.backgroundTintResource: Int
    get() = TODO()
    set(value) { backgroundTint = ContextCompat.getColor(context, value) }