package com.faltenreich.release.framework.android.view

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.android.context.screenSize
import com.faltenreich.release.framework.glide.GlideApp

fun ImageView.setWallpaper(
    release: Release?,
    size: Int = context.screenSize.x,
    callback: ((Drawable?) -> Unit)? = null
) {
    release?.imageUrlForWallpaper?.let { imageUrl ->
        setImageAsync(imageUrl, size, callback)
    } ?: run {
        setImageResource(Release.FALLBACK_COVER_COLOR_RES)
        callback?.invoke(null)
    }
}

fun ImageView.setCover(
    release: Release?,
    size: Int = context.screenSize.x / 2,
    callback: ((Drawable?) -> Unit)? = null
) {
    release?.imageUrlForCover?.let { imageUrl ->
        setImageAsync(imageUrl, size, callback)
    } ?: run {
        setImageResource(Release.FALLBACK_COVER_COLOR_RES)
        callback?.invoke(null)
    }
}

fun ImageView.setThumbnail(
    release: Release?,
    size: Int = context.screenSize.x / 4,
    callback: ((Drawable?) -> Unit)? = null
) {
    release?.imageUrlForThumbnail?.let { imageUrl ->
        setImageAsync(imageUrl, size, callback)
    } ?: run {
        setImageResource(Release.FALLBACK_COVER_COLOR_RES)
        callback?.invoke(null)
    }
}

private fun ImageView.setImageAsync(
    url: String,
    size: Int,
    callback: ((Drawable?) -> Unit)? = null
) {
    GlideApp
        .with(this)
        .load(url)
        .dontAnimate()
        .override(size)
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