package com.faltenreich.release.domain.release

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.android.context.screenSize
import com.faltenreich.release.framework.android.view.setImageAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val FALLBACK_COVER_COLOR_RES = R.color.colorPrimary

private fun ImageView.setImage(
    url: String?,
    size: Int,
    callback: ((Drawable?) -> Unit)?
) {
    url?.let {
        setImageAsync(url, size) { drawable ->
            GlobalScope.launch(Dispatchers.Main) {
                if (drawable == null) {
                    onImageNotFound(callback)
                } else {
                    callback?.invoke(null)
                }
            }
        }
    } ?: onImageNotFound(callback)
}

private fun ImageView.onImageNotFound(callback: ((Drawable?) -> Unit)?) {
    setImageResource(FALLBACK_COVER_COLOR_RES)
    callback?.invoke(null)
}

fun ImageView.setWallpaper(
    release: Release?,
    size: Int = context.screenSize.x,
    callback: ((Drawable?) -> Unit)? = null
) {
    setImage(release?.imageUrlForWallpaper, size, callback)
}

fun ImageView.setCover(
    release: Release?,
    size: Int = context.screenSize.x / 2,
    callback: ((Drawable?) -> Unit)? = null
) {
    setImage(release?.imageUrlForCover, size, callback)
}

fun ImageView.setThumbnail(
    release: Release?,
    size: Int = context.screenSize.x / 4,
    callback: ((Drawable?) -> Unit)? = null
) {
    setImage(release?.imageUrlForThumbnail, size, callback)
}