package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.BaseDao
import com.faltenreich.releaseradar.data.dao.Dao

abstract class Repository<Entity : com.faltenreich.releaseradar.data.model.Entity, DAO : BaseDao<Entity>>(val dao: DAO) : Dao<Entity> {
    override fun getById(id: String, onResult: (Entity?) -> Unit) = dao.getById(id, onResult)
    override fun getByIds(ids: Collection<String>, onResult: (List<Entity>) -> Unit) = dao.getByIds(ids, onResult)
}