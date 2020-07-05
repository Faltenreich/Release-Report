package com.faltenreich.release.framework.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.faltenreich.release.base.log.tag
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun String.toBitmap(
    context: Context,
    size: Int? = null,
    options: RequestOptions? = null
): Bitmap? {
    return suspendCoroutine { continuation ->
        try {
            GlideApp.with(context).asBitmap()
                .load(this)
                .run { size?.let { override(size, size) } ?: this }
                .run { options?.let { apply(options) } ?: this }
                .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    continuation.resume(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit

            })
        } catch (exception: Exception) {
            Log.e(tag, exception.toString())
            continuation.resume(null)
        }
    }
}