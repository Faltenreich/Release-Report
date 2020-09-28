package com.faltenreich.release.framework.android.animation.provider

import android.content.Context
import android.widget.ImageView
import androidx.core.view.isVisible
import com.faltenreich.release.framework.android.view.image.tint

class ImageViewTintProvider(private val imageView: ImageView) : ColorProvider {

    override var color: Int?
        get() = imageView.tint
        set(value) { imageView.tint = value }

    override val context: Context
        get() = imageView.context

    override val shouldAnimate: Boolean
        get() = imageView.isVisible
}