package com.faltenreich.release.framework.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.setImageAsync(
    url: String,
    placeholder: Drawable? = null,
    callback: ((Drawable?) -> Unit)? = null
) {
    GlideApp
        .with(this)
        .load(url)
        .placeholder(placeholder)
        .listener(object : RequestListener<Drawable> {

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                callback?.invoke(resource)
                return false
            }

            override fun onLoadFailed(
                exception: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                callback?.invoke(null)
                return false
            }
        })
        .into(this)
}