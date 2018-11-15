package com.faltenreich.releaseradar.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.releaseradar.data.model.Entity


class EntityDiffCallback<MODEL : Entity>(
    private val old: List<MODEL>,
    private val new: List<MODEL>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition].id == new[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = old[oldItemPosition] == new[newItemPosition]
}
