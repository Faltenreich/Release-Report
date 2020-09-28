package com.faltenreich.release.framework.android.animation

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.faltenreich.release.framework.android.view.backgroundTint

class ViewBackgroundColorProvider(private val view: View) : ColorProvider {

    override var color: Int?
        get() = view.backgroundTint
        set(value) { view.backgroundTint = value }

    override val context: Context
        get() = view.context

    override val shouldAnimate: Boolean
        get() = view.isVisible
}