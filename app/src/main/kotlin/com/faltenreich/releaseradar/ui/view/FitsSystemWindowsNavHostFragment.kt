package com.faltenreich.releaseradar.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.NavHostFragment

class FitsSystemWindowsNavHostFragment : NavHostFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val coordinatorLayout = CoordinatorLayout(inflater.context)
        coordinatorLayout.fitsSystemWindows = true
        coordinatorLayout.id = id
        return coordinatorLayout
    }
}