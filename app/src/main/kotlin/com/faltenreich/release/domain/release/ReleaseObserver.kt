package com.faltenreich.release.domain.release

import com.faltenreich.release.data.model.Release

interface ReleaseProvider {
    var release: Release?
}