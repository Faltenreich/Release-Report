package com.faltenreich.release.domain.release.search

import androidx.fragment.app.Fragment
import com.lapism.searchview.widget.SearchView

data class SearchableProperties(
    val fragment: Fragment,
    val searchView: SearchView
)