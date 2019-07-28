package com.faltenreich.release.ui.logic.opener

import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.faltenreich.release.MainNavigationDirections
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.fragment.ReleaseDetailFragment

interface ReleaseOpener {
    fun openRelease(navigationController: NavController, release: Release, sharedElement: View? = null) {
        release.id?.also { id ->
            val destination = MainNavigationDirections.openRelease(id)
            sharedElement?.let { sharedElement ->
                ViewCompat.setTransitionName(sharedElement, ReleaseDetailFragment.SHARED_ELEMENT_NAME)
                navigationController.navigate(
                    destination,
                    FragmentNavigatorExtras(sharedElement to ReleaseDetailFragment.SHARED_ELEMENT_NAME)
                )
            } ?: navigationController.navigate(destination)
        }
    }
}