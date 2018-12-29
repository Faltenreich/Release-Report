package com.faltenreich.releaseradar.data.model

import androidx.recyclerview.widget.DiffUtil
import com.faltenreich.releaseradar.firebase.database.model.FirebaseEntity
import com.google.firebase.database.Exclude

abstract class Entity(
    @get:Exclude @set:Exclude
    override var id: String? = null
) : FirebaseEntity {
    override fun equals(other: Any?): Boolean = other == this || (other is Entity && other.id == id)
}