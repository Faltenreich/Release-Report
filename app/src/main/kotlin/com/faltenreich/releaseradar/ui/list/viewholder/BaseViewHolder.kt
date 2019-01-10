package com.faltenreich.releaseradar.ui.list.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder <MODEL : Any> (
        protected val context: Context,
        @LayoutRes layoutResId: Int,
        parent: ViewGroup,
        itemView: View = LayoutInflater.from(context).inflate(layoutResId, parent, false)
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View? = itemView

    fun bind(data: MODEL) {
        onBind(data)
    }

    protected abstract fun onBind(data: MODEL)
}
