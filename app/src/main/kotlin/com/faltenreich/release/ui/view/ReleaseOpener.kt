package com.faltenreich.release.ui.view

import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.faltenreich.release.NavigationGraphDirections
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.fragment.ReleaseFragment

interface ReleaseOpener {
    fun openRelease(release: Release, sharedElement: View) {
        release.id?.also { id ->
            ViewCompat.setTransitionName(sharedElement, ReleaseFragment.SHARED_ELEMENT_NAME)
            sharedElement.findNavController().navigate(
                NavigationGraphDirections.openRelease(id),
                FragmentNavigatorExtras(sharedElement to ReleaseFragment.SHARED_ELEMENT_NAME)
            )
        }
    }
}