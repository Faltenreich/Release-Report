package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.Dao
import com.faltenreich.releaseradar.data.model.Model

abstract class Repository<T : Model, D : Dao<T>>(val dao: D) : Dao<T> {
    override fun getById(id: String, onResult: (T?) -> Unit) = dao.getById(id, onResult)
    override fun getByIds(ids: Collection<String>, onResult: (List<T>) -> Unit) = dao.getByIds(ids, onResult)
}