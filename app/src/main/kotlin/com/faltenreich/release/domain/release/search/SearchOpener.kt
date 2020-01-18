package com.faltenreich.release.domain.release.search

import androidx.navigation.NavController

interface SearchOpener {

    fun openSearch(navigationController: NavController, query: String? = null) {
        navigationController.navigate(SearchFragmentDirections.openSearch(query ?: ""))
    }
}