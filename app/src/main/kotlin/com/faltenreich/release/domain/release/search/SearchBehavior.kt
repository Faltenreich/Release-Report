package com.faltenreich.release.domain.release.search

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.lapism.searchview.widget.SearchBehavior
import com.lapism.searchview.widget.SearchView
import kotlin.math.abs

class SearchBehavior @JvmOverloads constructor(context: Context? = null, attributeSet: AttributeSet? = null) : SearchBehavior(context, attributeSet) {
    private var show: Boolean = true

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: SearchView, dependency: View): Boolean {
        (dependency as? AppBarLayout)?.let { appBarLayout ->
            val currentScrollRangeY = dependency.y
            val totalScrollRangeY = appBarLayout.totalScrollRange
            val appBarLayoutIsCollapsed = totalScrollRangeY > 0 && abs(currentScrollRangeY) >= totalScrollRangeY
            if (show != !appBarLayoutIsCollapsed) {
                val alpha = if (appBarLayoutIsCollapsed) 0f else 1f
                val duration = if (appBarLayoutIsCollapsed) ANIMATION_DURATION_HIDE else ANIMATION_DURATION_SHOW
                child.animate().alpha(alpha).setDuration(duration).start()
                show = !appBarLayoutIsCollapsed
            }
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }

    companion object {
        private const val ANIMATION_DURATION_SHOW = 200L
        private const val ANIMATION_DURATION_HIDE = 400L
    }
}