package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Entity

interface Dao<T : Entity> {
    fun getById(id: String, onResult: (T?) -> Unit)
    fun getByIds(ids: Collection<String>, onResult: (List<T>) -> Unit)
}
