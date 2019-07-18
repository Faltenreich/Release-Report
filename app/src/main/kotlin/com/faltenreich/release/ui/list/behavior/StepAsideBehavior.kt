package com.faltenreich.release.ui.list.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

open class StepAsideBehavior(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var snackbarTranslation: Float = 0.toFloat()

    @Override
    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is Snackbar.SnackbarLayout || dependency is RecyclerView
    }

    @Override
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        if (dependency is Snackbar.SnackbarLayout) {
            translateWithSnackbar(parent, child, dependency as Snackbar.SnackbarLayout)
            return true
        }
        return false
    }

    private fun translateWithSnackbar(parent: CoordinatorLayout, child: View, snackbarLayout: Snackbar.SnackbarLayout) {
        var translationY = 0.0f
        val dependencies = parent.getDependencies(child)
        var i = 0
        val z = dependencies.size
        while (i < z) {
            val view = dependencies.get(i) as View
            if (view is Snackbar.SnackbarLayout && parent.doViewsOverlap(child, view)) {
                translationY = Math.min(translationY, view.getTranslationY() - view.getHeight() as Float)
            }
            ++i
        }
        if (translationY != snackbarTranslation) {
            ViewCompat.animate(child).cancel()
            if (Math.abs(translationY - snackbarTranslation) === snackbarLayout.getHeight() as Float) {
                ViewCompat.animate(child).translationY(translationY).setListener(null)
            } else {
                child.setTranslationY(translationY)
            }
            snackbarTranslation = translationY
        }
    }
}