package com.faltenreich.release.domain.release

import androidx.navigation.NavController
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.domain.media.image.ImageOpener

interface ReleaseImageOpener : ImageOpener {

    fun openImage(
        navigationController: NavController,
        release: Release?,
        imageUrl: String?
    ) {
        openImage(navigationController, release?.imageUrlsFull?.toTypedArray(), imageUrl)
    }
}