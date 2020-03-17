package com.faltenreich.release.framework.skeleton

import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.release.framework.android.context.getColorFromAttribute
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.createSkeleton

object SkeletonFactory {

    fun createSkeleton(recyclerView: RecyclerView, @LayoutRes layoutResId: Int, itemCount: Int): Skeleton {
        val context = recyclerView.context
        return recyclerView.applySkeleton(
            layoutResId,
            itemCount = itemCount,
            maskColor = context.getColorFromAttribute(R.attr.backgroundColorPrimary),
            shimmerColor = context.getColorFromAttribute(R.attr.backgroundColorTertiary),
            cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.card_corner_radius)?.toFloat() ?: 0f)
    }
}