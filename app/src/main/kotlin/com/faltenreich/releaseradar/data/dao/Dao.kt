package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Entity

interface Dao<T : Entity> {

    fun getAll(onSuccess: (List<T>) -> Unit, onError: ((Exception?) -> Unit)? = null)

    fun getById(id: String, onSuccess: (T?) -> Unit, onError: ((Exception?) -> Unit)? = null)

    fun getByIds(ids: Collection<String>, onSuccess: (List<T>) -> Unit, onError: ((Exception?) -> Unit)?)

    fun createOrUpdate(entity: T, onSuccess: (() -> Unit)? = null, onError: ((Exception?) -> Unit)? = null)

    fun delete(entity: T, onSuccess: (() -> Unit)? = null, onError: ((Exception?) -> Unit)? = null)
}
