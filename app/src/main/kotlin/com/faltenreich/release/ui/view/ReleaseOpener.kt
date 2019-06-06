package com.faltenreich.release.ui.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.faltenreich.release.R
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.ui.fragment.ReleaseDetailFragment

interface ReleaseOpener {
    fun openRelease(context: Context, release: Release, sharedElement: View) {
        release.id?.also { id ->
            ViewCompat.setTransitionName(sharedElement, ReleaseDetailFragment.SHARED_ELEMENT_NAME)
            (context as? Activity)?.findNavController(R.id.appNavigationHost)?.navigate(
                R.id.open_release,
                Bundle().apply { putString("releaseId", id) },
                null,
                FragmentNavigatorExtras(sharedElement to ReleaseDetailFragment.SHARED_ELEMENT_NAME)
            )
        }
    }
}