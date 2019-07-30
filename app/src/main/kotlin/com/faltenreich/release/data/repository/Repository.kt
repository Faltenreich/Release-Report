package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.Dao
import com.faltenreich.release.data.dao.DaoFactory
import com.faltenreich.release.data.model.Model
import kotlin.reflect.KClass

abstract class Repository<T : Model, D : Dao<T>>(clazz: KClass<D>) {
    protected val dao = DaoFactory.dao(clazz)
    fun getById(id: String, onResult: (T?) -> Unit) = dao.getById(id, onResult)
    fun getByIds(ids: Collection<String>, onResult: (List<T>) -> Unit) = dao.getByIds(ids, onResult)
}