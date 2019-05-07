package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.BaseDao
import com.faltenreich.releaseradar.data.dao.Dao

abstract class Repository<Entity : com.faltenreich.releaseradar.data.model.Entity, DAO : BaseDao<Entity>>(val dao: DAO) : Dao<Entity> {
    override fun getAll(onSuccess: (List<Entity>) -> Unit, onError: ((Exception?) -> Unit)?) = dao.getAll(onSuccess, onError)
    override fun getById(id: String, onSuccess: (Entity?) -> Unit, onError: ((Exception?) -> Unit)?) = dao.getById(id, onSuccess, onError)
    override fun getByIds(ids: Collection<String>, onSuccess: (List<Entity>) -> Unit, onError: ((Exception?) -> Unit)?) = dao.getByIds(ids, onSuccess, onError)
    override fun createOrUpdate(entity: Entity, onSuccess: (() -> Unit)?, onError: ((Exception?) -> Unit)?) = dao.createOrUpdate(entity, onSuccess, onError)
    override fun delete(entity: Entity, onSuccess: (() -> Unit)?, onError: ((Exception?) -> Unit)?) = dao.delete(entity, onSuccess, onError)
}