package com.faltenreich.releaseradar.parse.database

import com.faltenreich.releaseradar.data.dao.Dao
import com.faltenreich.releaseradar.data.model.Entity
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlin.reflect.KClass

abstract class ParseDao<T : Entity>(private val clazz: KClass<T>) : Dao<T> {
    abstract val entityName: String

    protected fun getQuery(): ParseQuery<ParseObject> {
        return ParseQuery.getQuery<ParseObject>(entityName)
    }

    protected fun findInBackground(query: ParseQuery<ParseObject>, onSuccess: (List<T>) -> Unit, onError: ((Exception?) -> Unit)?) {
        query.findInBackground { parseObjects, exception ->
            if (exception == null) {
                val entities = parseObjects.mapNotNull { parseObject -> ParseObjectFactory.createEntity(clazz, parseObject) }
                onSuccess(entities)
            } else {
                onError?.invoke(exception)
            }
        }
    }

    override fun getAll(onSuccess: (List<T>) -> Unit, onError: ((Exception?) -> Unit)?) {
        val query = getQuery()
        findInBackground(query, onSuccess, onError)
    }

    override fun getById(id: String, onSuccess: (T?) -> Unit, onError: ((Exception?) -> Unit)?) {
        getQuery().getInBackground(id) { parseObject, exception ->
            ParseObjectFactory.createEntity(clazz, parseObject)?.let { storable -> onSuccess(storable) } ?: onError?.invoke(exception)
        }
    }

    override fun createOrUpdate(entity: T, onSuccess: (() -> Unit)?, onError: ((Exception?) -> Unit)?) {
        ParseObjectFactory.createParseObject(entity)?.let { parseObject ->
            parseObject.saveInBackground { exception -> exception?.let { onError?.invoke(exception) } ?: onSuccess?.invoke() }
        } ?: onError?.invoke(null)
    }

    override fun delete(entity: T, onSuccess: (() -> Unit)?, onError: ((Exception?) -> Unit)?) {
        // TODO
    }
}