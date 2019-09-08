package com.faltenreich.release.framework.glide

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.faltenreich.release.base.log.tag

fun String.toBitmap(context: Context): Bitmap? {
    return try {
        GlideApp.with(context).asBitmap().load(this).submit().get()
    } catch (exception: Exception) {
        Log.e(tag, exception.message)
        return null
    }
}