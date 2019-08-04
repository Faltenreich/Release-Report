package com.faltenreich.release.ui.view

import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.createSkeleton

object SkeletonFactory {
    fun createSkeleton(recyclerView: RecyclerView, @LayoutRes layoutResId: Int, itemCount: Int): Skeleton {
        val context = recyclerView.context
        return recyclerView.applySkeleton(
            layoutResId,
            itemCount = itemCount,
            maskColor = ContextCompat.getColor(context, R.color.colorPrimary),
            shimmerColor = ContextCompat.getColor(context, R.color.blue_gray),
            cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.card_corner_radius)?.toFloat() ?: 0f)
    }
    fun createSkeleton(view: View): Skeleton {
        val context = view.context
        return view.createSkeleton(
            maskColor = ContextCompat.getColor(context, R.color.colorPrimary),
            shimmerColor = ContextCompat.getColor(context, R.color.blue_gray),
            cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.card_corner_radius)?.toFloat() ?: 0f)
    }
}