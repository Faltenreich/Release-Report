package com.faltenreich.release.domain.release.list

import com.faltenreich.release.data.model.Release

interface ReleaseProvider {
    val release: Release
    override fun equals(other:Any?): Boolean
}