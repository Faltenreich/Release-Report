package com.faltenreich.releaseradar.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.releaseradar.data.model.Entity

class EntityDiffUtilItemCallback<MODEL : Entity> : DiffUtil.ItemCallback<MODEL>() {
    override fun areItemsTheSame(oldItem: MODEL, newItem: MODEL): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: MODEL, newItem: MODEL): Boolean = oldItem == newItem
}