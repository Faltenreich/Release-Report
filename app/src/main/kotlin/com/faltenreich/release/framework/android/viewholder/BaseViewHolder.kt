package com.faltenreich.release.framework.android.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<MODEL : Any>(
    protected val context: Context,
    @LayoutRes layoutResId: Int,
    parent: ViewGroup,
    itemView: View = LayoutInflater.from(context).inflate(layoutResId, parent, false)
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View? = itemView

    protected lateinit var data: MODEL

    protected val navigationController: NavController
        get() = itemView.findNavController()

    fun bind(data: MODEL) {
        this.data = data
        onBind(data)
    }

    protected abstract fun onBind(data: MODEL)
}
