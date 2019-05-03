package com.faltenreich.releaseradar.parse.database

import com.faltenreich.releaseradar.data.dao.Dao
import com.faltenreich.releaseradar.data.model.Entity
import com.faltenreich.releaseradar.firebase.database.FirebaseQuery
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlin.reflect.KClass

abstract class ParseDao<T : Entity>(private val clazz: KClass<T>) : Dao<T> {

    override fun getAll(query: FirebaseQuery?, onSuccess: (List<T>) -> Unit, onError: ((Exception?) -> Unit)?) {
        ParseQuery.getQuery<ParseObject>("Release").findInBackground { parseObjects, exception ->
            if (exception == null) {
                val entities = parseObjects.mapNotNull { parseObject -> ParseObjectFactory.createEntity(clazz, parseObject) }
                onSuccess(entities)
            } else {
                onError?.invoke(exception)
            }
        }
    }

    override fun getById(id: String, onSuccess: (T?) -> Unit, onError: ((Exception?) -> Unit)?) {
        ParseQuery.getQuery<ParseObject>("").getInBackground(id) { parseObject, exception ->
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