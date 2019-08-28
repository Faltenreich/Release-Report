package com.faltenreich.release.framework.android.view

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.faltenreich.release.base.image.GlideApp

fun ImageView.setImageAsync(url: String, size: Int? = null, callback: ((Drawable?) -> Unit)? = null) {
    GlideApp
        .with(this)
        .load(url)
        .dontAnimate()
        .apply { size?.let { override(size) } }
        .listener(object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                callback?.invoke(resource)
                return false
            }
            override fun onLoadFailed(exception: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                callback?.invoke(null)
                return false
            }
        })
        .into(this)
}