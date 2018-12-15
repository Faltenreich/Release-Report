package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.Dao
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Entity
import com.faltenreich.releaseradar.firebase.database.dao.FirebaseDaoApi

abstract class Repository<MODEL : Entity, DAO : Dao<MODEL>>(private val dao: DAO) : FirebaseDaoApi<MODEL> {
    override fun generateId(path: String): String? = dao.generateId(path)
    override fun getAll(query: Query?, onSuccess: (List<MODEL>) -> Unit, onError: ((Exception) -> Unit)?) = dao.getAll(query, onSuccess, onError)
    override fun getById(id: String, onSuccess: (MODEL?) -> Unit, onError: ((Exception) -> Unit)?) = dao.getById(id, onSuccess, onError)
    override fun createOrUpdate(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) = dao.createOrUpdate(entity, onSuccess, onError)
    override fun delete(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) = dao.delete(entity, onSuccess, onError)
}