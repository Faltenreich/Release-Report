package com.faltenreich.release.domain.release.search

import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.lapism.searchview.widget.SearchView

class SearchableObserver: LifecycleObserver {
    lateinit var properties: SearchableProperties

    private val fragment: Fragment
        get() = properties.fragment

    private val searchView: SearchView
        get() = properties.searchView

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        searchView.doOnPreDraw {
            // Workaround to reset shadow after onRestoreInstanceState
            searchView.setShadow(true)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        searchView.setQuery(null, false)
        searchView.clearFocus()
        fragment.view?.requestFocus()
        searchView.hideKeyboard()
        // Workaround to reset shadow after onPause
        searchView.setShadow(true)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        // Workaround to prevent visible shadow onResume
        searchView.setShadow(false)
    }
}