package com.faltenreich.release.ui.logic.opener

import androidx.navigation.NavController
import com.faltenreich.release.ui.fragment.SearchFragmentDirections

interface SearchOpener {
    fun openSearch(navigationController: NavController, query: String? = null) {
        navigationController.navigate(SearchFragmentDirections.openSearch(query ?: ""))
    }
}