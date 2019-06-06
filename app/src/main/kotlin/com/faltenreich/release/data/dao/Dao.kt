package com.faltenreich.release.data.dao

import com.faltenreich.release.data.model.Model

interface Dao<T : Model> {
    fun getById(id: String, onResult: (T?) -> Unit)
    fun getByIds(ids: Collection<String>, onResult: (List<T>) -> Unit)
}