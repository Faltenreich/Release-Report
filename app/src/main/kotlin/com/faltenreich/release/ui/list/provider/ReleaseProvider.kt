package com.faltenreich.release.ui.list.provider

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.item.DateStyle

interface ReleaseProvider {
    val release: Release
    val dateStyle: DateStyle
    override fun equals(other:Any?): Boolean
}