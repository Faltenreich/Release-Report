package com.faltenreich.release.domain.release.detail

import android.view.View
import androidx.navigation.NavController
import com.faltenreich.release.MainNavigationDirections
import com.faltenreich.release.data.model.Release
import com.faltenreich.release.framework.kotlinx.JsonParser

interface ReleaseOpener {

    fun openRelease(navigationController: NavController, release: Release, sharedElement: View? = null) {
        val json = JsonParser.parseToJson(Release.serializer(), release)
        navigationController.navigate(MainNavigationDirections.openRelease(json))
    }
}