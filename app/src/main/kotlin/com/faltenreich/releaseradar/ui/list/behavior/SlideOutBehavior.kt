package com.faltenreich.releaseradar.ui.list.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

class SlideOutBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : StepAsideBehavior(context, attrs) {
    private var margin: Float = -1f
    var isEnabled = true

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (margin < 0f) {
            margin = (child.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin.toFloat()
        }
        if (isEnabled) {
            child.translationY = Math.max(0f, Math.min(child.height + margin, child.translationY + dy.toFloat()))
        }
    }
}