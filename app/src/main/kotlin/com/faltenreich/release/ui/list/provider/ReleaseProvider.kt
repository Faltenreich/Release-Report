package com.faltenreich.release.ui.list.provider

import com.faltenreich.release.data.model.Release

interface ReleaseProvider {
    val release: Release
    override fun equals(other:Any?): Boolean
}