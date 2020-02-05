package com.faltenreich.release.data.dao

import com.faltenreich.release.data.model.Model

interface Dao<T : Model> {
    suspend fun getById(id: String): T?
    suspend fun getByIds(ids: Collection<String>): List<T>
}
