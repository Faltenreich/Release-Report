package com.faltenreich.release.ui.logic.opener

import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.faltenreich.release.AppNavigationDirections
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.fragment.GalleryFragment

interface MediaOpener {
    fun openMedia(navigationController: NavController, release: Release, media: Media? = null, sharedElement: View) {
        release.id?.also { releaseId ->
            ViewCompat.setTransitionName(sharedElement, GalleryFragment.SHARED_ELEMENT_NAME)
            navigationController.navigate(
                AppNavigationDirections.openGallery(releaseId, media?.id),
                FragmentNavigatorExtras(sharedElement to GalleryFragment.SHARED_ELEMENT_NAME)
            )
        }
    }
}