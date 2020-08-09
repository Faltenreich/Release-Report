package com.faltenreich.release.domain.media.image

import androidx.navigation.NavController

interface ImageOpener {

    fun openImage(
        navigationController: NavController,
        imageUrls: Array<String>?,
        imageUrl: String
    ) {
        navigationController.navigate(ImageGalleryFragmentDirections.openGallery(imageUrls, imageUrl))
    }
}