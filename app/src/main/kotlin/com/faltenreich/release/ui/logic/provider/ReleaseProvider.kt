package com.faltenreich.release.ui.logic.provider

import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.list.item.DateStyle

interface ReleaseProvider {
    val release: Release
    override fun equals(other:Any?): Boolean
}