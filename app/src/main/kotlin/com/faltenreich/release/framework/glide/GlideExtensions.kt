package com.faltenreich.release.framework.glide

import android.content.Context
import android.graphics.Bitmap

fun String.toBitmap(context: Context): Bitmap? {
    return GlideApp.with(context).asBitmap().load(this).submit().get()
}