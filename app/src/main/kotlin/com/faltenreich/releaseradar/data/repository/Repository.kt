package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.BaseDao
import com.faltenreich.releaseradar.data.dao.Dao
import com.faltenreich.releaseradar.data.dao.Query
import com.faltenreich.releaseradar.data.model.Storable

abstract class Repository<Entity : Storable, DAO : BaseDao<Entity>>(private val dao: DAO) : Dao<Entity> {
    override fun getAll(query: Query?, onSuccess: (List<Entity>) -> Unit, onError: ((Exception?) -> Unit)?) = dao.getAll(query, onSuccess, onError)
    override fun getById(id: String, onSuccess: (Entity?) -> Unit, onError: ((Exception?) -> Unit)?) = dao.getById(id, onSuccess, onError)
    override fun createOrUpdate(entity: Entity, onSuccess: (() -> Unit)?, onError: ((Exception?) -> Unit)?) = dao.createOrUpdate(entity, onSuccess, onError)
    override fun delete(entity: Entity, onSuccess: (() -> Unit)?, onError: ((Exception?) -> Unit)?) = dao.delete(entity, onSuccess, onError)
}