package com.faltenreich.release.domain.release.search

import androidx.fragment.app.Fragment
import com.lapism.search.widget.MaterialSearchView

data class SearchableProperties(
    val fragment: Fragment,
    val searchView: MaterialSearchView
)