package com.faltenreich.releaseradar.ui.fragment

import android.graphics.Rect
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.appbar.AppBarLayout
import com.lapism.searchview.widget.SearchView

class SearchableObserver: LifecycleObserver {

    lateinit var properties: SearchableProperties

    private val fragment: Fragment
        get() = properties.fragment

    private val searchView: SearchView
        get() = properties.searchView

    private val appBarLayout: AppBarLayout?
        get() = properties.appBarLayout

    private val statusBarBackground: View?
        get() = properties.statusBarBackground

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        searchView.doOnPreDraw {
            // FIXME: Workaround to reset shadow after onRestoreInstanceState
            searchView.setShadow(true)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        fragment.view?.doOnPreDraw {
            val frame = Rect()
            fragment.activity?.window?.decorView?.getWindowVisibleDisplayFrame(frame)
            appBarLayout?.setPadding(0, frame.top, 0, 0)
            searchView.setPadding(0, frame.top, 0, 0)
            statusBarBackground?.layoutParams?.height = frame.top
        }

        searchView.setQuery(null, false)
        searchView.clearFocus()
        fragment.view?.requestFocus()
        searchView.hideKeyboard()
        // FIXME: Workaround to reset shadow after onPause
        searchView.setShadow(true)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        // FIXME: Workaround to prevent visible shadow onResume
        searchView.setShadow(false)
    }
}