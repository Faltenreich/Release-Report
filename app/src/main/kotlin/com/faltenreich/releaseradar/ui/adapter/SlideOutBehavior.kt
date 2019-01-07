package com.faltenreich.releaseradar.ui.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

class SlideOutBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : StepAsideBehavior(context, attrs) {

    var maySlideIn = true
    var maySlideOut = true

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (!maySlideIn) {
            // TODO: Transition translation
            translateWithScrollView(child, child.height.toFloat())
        } else if (maySlideOut) {
            translateWithScrollView(child, dy.toFloat())
        }
    }

    private fun translateWithScrollView(child: View, dy: Float) {
        val margin = if (child.layoutParams is ViewGroup.MarginLayoutParams) (child.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin.toFloat() else 0f
        child.translationY = Math.max(0f, Math.min(child.height + margin, child.translationY + dy))
    }
}