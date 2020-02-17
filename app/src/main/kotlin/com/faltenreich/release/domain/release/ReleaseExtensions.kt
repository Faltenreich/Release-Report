package com.faltenreich.release.domain.release

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.android.view.setImageAsync

private fun ImageView.setImage(
    url: String?,
    placeholderColorRes: Int?,
    callback: ((Drawable?) -> Unit)?
) {
    url?.let {
        val placeholder: Drawable? = placeholderColorRes?.let { res ->
            ColorDrawable(ContextCompat.getColor(context, res))
        }
        setImageAsync(url, placeholder) { drawable ->
            if (drawable == null) {
                onImageNotFound(placeholderColorRes, callback)
            } else {
                callback?.invoke(drawable)
            }
        }
    } ?: onImageNotFound(placeholderColorRes, callback)
}

private fun ImageView.onImageNotFound(
    placeholderColorRes: Int?,
    callback: ((Drawable?) -> Unit)?
) {
    setImageResource(placeholderColorRes ?: R.color.colorPrimary)
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