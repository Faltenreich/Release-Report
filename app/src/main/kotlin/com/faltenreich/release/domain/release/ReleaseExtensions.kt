package com.faltenreich.release.domain.release

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.android.context.getColorFromAttribute
import com.faltenreich.release.framework.glide.setImageAsync

private fun ImageView.setImage(
    url: String?,
    callback: ((Drawable?) -> Unit)?
) {
    val placeholder = ColorDrawable(context.getColorFromAttribute(R.attr.backgroundColorSecondary))
    url?.let {
        setImageAsync(url, placeholder) { drawable ->
            if (drawable == null) {
                onImageNotFound(placeholder, callback)
            } else {
                callback?.invoke(drawable)
            }
        }
    } ?: onImageNotFound(placeholder, callback)
}

private fun ImageView.onImageNotFound(
    placeholder: Drawable,
    callback: ((Drawable?) -> Unit)?
) {
    setImageDrawable(placeholder)
    callback?.invoke(null)
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