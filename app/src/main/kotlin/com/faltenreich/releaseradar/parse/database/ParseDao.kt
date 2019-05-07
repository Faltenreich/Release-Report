package com.faltenreich.releaseradar.parse.database

import com.faltenreich.releaseradar.data.dao.Dao
import com.faltenreich.releaseradar.data.model.BaseEntity
import com.faltenreich.releaseradar.data.model.Entity
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlin.reflect.KClass

abstract class ParseDao<T : Entity>(private val clazz: KClass<T>) : Dao<T> {
    abstract val entityName: String

    protected fun getQuery(): ParseQuery<ParseObject> {
        return ParseQuery.getQuery<ParseObject>(entityName)
    }

    protected fun ParseQuery<ParseObject>.findInBackground(onSuccess: (List<T>) -> Unit, onError: ((Exception?) -> Unit)?) {
        findInBackground { parseObjects, exception ->
            if (exception == null) {
                val entities = parseObjects.mapNotNull { parseObject -> ParseObjectFactory.createEntity(clazz, parseObject) }
                onSuccess(entities)
            } else {
                onError?.invoke(exception)
            }
        }
    }

    override fun getById(id: String, onSuccess: (T?) -> Unit, onError: ((Exception?) -> Unit)?) {
        getQuery().whereEqualTo(BaseEntity.ID, id).findInBackground({ entities -> onSuccess(entities.firstOrNull()) }, onError)
    }

    override fun getByIds(ids: Collection<String>, onSuccess: (List<T>) -> Unit, onError: ((Exception?) -> Unit)?) {
        getQuery().whereContainedIn(BaseEntity.ID, ids).findInBackground(onSuccess, onError)
    }
}