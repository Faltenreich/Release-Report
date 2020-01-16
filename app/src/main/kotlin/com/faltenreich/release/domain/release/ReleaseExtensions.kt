package com.faltenreich.release.domain.release

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.android.context.screenSize
import com.faltenreich.release.framework.android.view.setImageAsync

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