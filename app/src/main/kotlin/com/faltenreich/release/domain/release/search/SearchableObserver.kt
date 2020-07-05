package com.faltenreich.release.domain.release.search

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.lapism.search.widget.MaterialSearchView

class SearchableObserver: LifecycleObserver {
    lateinit var properties: SearchableProperties

    private val fragment: Fragment
        get() = properties.fragment

    private val searchView: MaterialSearchView
        get() = properties.searchView

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        searchView.setTextQuery(null, false)
        searchView.clearFocus()
        fragment.view?.requestFocus()
        searchView.hideKeyboard()
    }
}