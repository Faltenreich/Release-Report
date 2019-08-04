package com.faltenreich.release.ui.view

import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.release.R
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

object SkeletonFactory {
    fun createSkeleton(listView: RecyclerView, @LayoutRes layoutResId: Int, itemCount: Int): Skeleton {
        val context = listView.context
        return listView.applySkeleton(
            layoutResId,
            itemCount = itemCount,
            maskColor = ContextCompat.getColor(context, R.color.colorPrimary),
            shimmerColor = ContextCompat.getColor(context, R.color.blue_gray),
            cornerRadius = context?.resources?.getDimensionPixelSize(R.dimen.card_corner_radius)?.toFloat() ?: 0f)
    }
}