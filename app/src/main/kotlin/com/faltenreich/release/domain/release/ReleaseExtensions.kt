package com.faltenreich.release.domain.release

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.glide.setImageAsync

private fun ImageView.setImage(
    url: String?,
    callback: ((Drawable?) -> Unit)?
) {
    // FIXME: Missing fallback
    url?.let {
        setImageAsync(url, null, callback)
    } ?: callback?.invoke(null)
}

fun ImageView.setWallpaper(
    release: Release?,
    callback: ((Drawable?) -> Unit)? = null
) = setImage(release?.imageUrlForWallpaper, callback)

fun ImageView.setCover(
    release: Release?,
    callback: ((Drawable?) -> Unit)? = null
) = setImage(release?.imageUrlForCover, callback)

fun ImageView.setThumbnail(
    release: Release?,
    callback: ((Drawable?) -> Unit)? = null
) = setImage(release?.imageUrlForThumbnail, callback)