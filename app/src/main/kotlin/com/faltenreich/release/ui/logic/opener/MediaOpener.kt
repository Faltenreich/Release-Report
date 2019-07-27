package com.faltenreich.release.ui.logic.opener

import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.faltenreich.release.NavigationGraphDirections
import com.faltenreich.release.data.model.Media
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.fragment.GalleryFragment
import com.faltenreich.release.ui.fragment.ReleaseDetailFragment

interface MediaOpener {
    fun openMedia(navigationController: NavController, release: Release, media: Media? = null, sharedElement: View) {
        release.id?.also { releaseId ->
            ViewCompat.setTransitionName(sharedElement, GalleryFragment.SHARED_ELEMENT_NAME)
            navigationController.navigate(
                NavigationGraphDirections.openGallery(releaseId, media?.id),
                FragmentNavigatorExtras(sharedElement to GalleryFragment.SHARED_ELEMENT_NAME)
            )
        }
    }
}