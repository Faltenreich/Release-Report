package com.faltenreich.release.domain.release

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.android.context.screenSize
import com.faltenreich.release.framework.android.view.setImageAsync

private fun ImageView.setImage(
    url: String?,
    size: Int,
    fallbackColor: Int?,
    callback: ((Drawable?) -> Unit)?
) {
    url?.let {
        setImageAsync(url, size) { drawable ->
            if (drawable == null) {
                onImageNotFound(fallbackColor, callback)
            } else {
                callback?.invoke(drawable)
            }
        }
    } ?: onImageNotFound(fallbackColor, callback)
}

private fun ImageView.onImageNotFound(
    fallbackColor: Int?,
    callback: ((Drawable?) -> Unit)?
) {
    setImageResource(fallbackColor ?: R.color.colorPrimary)
    callback?.invoke(null)
}

fun ImageView.setWallpaper(
    release: Release?,
    size: Int = context.screenSize.x,
    callback: ((Drawable?) -> Unit)? = null
) {
    setImage(release?.imageUrlForWallpaper, size, release?.releaseType?.colorResId, callback)
}

fun ImageView.setCover(
    release: Release?,
    size: Int = context.screenSize.x / 2,
    callback: ((Drawable?) -> Unit)? = null
) {
    setImage(release?.imageUrlForCover, size, release?.releaseType?.colorResId, callback)
}

fun ImageView.setThumbnail(
    release: Release?,
    size: Int = context.screenSize.x / 4,
    callback: ((Drawable?) -> Unit)? = null
) {
    setImage(release?.imageUrlForThumbnail, size, release?.releaseType?.colorResId, callback)
}