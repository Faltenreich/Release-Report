package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.firebase.database.FirebaseQuery

interface Dao<Entity : com.faltenreich.releaseradar.data.model.Entity> {

    fun getAll(query: FirebaseQuery? = null, onSuccess: (List<Entity>) -> Unit, onError: ((Exception?) -> Unit)? = null)

    fun getById(id: String, onSuccess: (Entity?) -> Unit, onError: ((Exception?) -> Unit)? = null)

    fun createOrUpdate(entity: Entity, onSuccess: (() -> Unit)? = null, onError: ((Exception?) -> Unit)? = null)

    fun delete(entity: Entity, onSuccess: (() -> Unit)? = null, onError: ((Exception?) -> Unit)? = null)
}
