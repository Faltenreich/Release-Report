package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Entity

interface Dao<T : Entity> {
    fun getById(id: String, onSuccess: (T?) -> Unit, onError: ((Exception?) -> Unit)? = null)
    fun getByIds(ids: Collection<String>, onSuccess: (List<T>) -> Unit, onError: ((Exception?) -> Unit)?)
}
