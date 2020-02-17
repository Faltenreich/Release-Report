package com.faltenreich.release.domain.release

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.android.view.setImageAsync

private fun ImageView.setImage(
    url: String?,
    fallbackColor: Int?,
    callback: ((Drawable?) -> Unit)?
) {
    url?.let {
        setImageAsync(url) { drawable ->
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
    callback: ((Drawable?) -> Unit)? = null
) {
    setImage(release?.imageUrlForWallpaper, release?.releaseType?.colorResId, callback)
}

fun ImageView.setCover(
    release: Release?,
    callback: ((Drawable?) -> Unit)? = null
) {
    setImage(release?.imageUrlForCover, release?.releaseType?.colorResId, callback)
}

fun ImageView.setThumbnail(
    release: Release?,
    callback: ((Drawable?) -> Unit)? = null
) {
    setImage(release?.imageUrlForThumbnail, release?.releaseType?.colorResId, callback)
}