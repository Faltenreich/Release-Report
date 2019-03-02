package com.faltenreich.releaseradar.ui.fragment

import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.lapism.searchview.widget.SearchView

data class SearchableProperties(
    val fragment: Fragment,
    val searchView: SearchView,
    val appBarLayout: AppBarLayout? = null
)