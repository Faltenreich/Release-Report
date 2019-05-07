package com.faltenreich.releaseradar.parse.database

import android.util.Log
import com.faltenreich.releaseradar.data.dao.Dao
import com.faltenreich.releaseradar.data.model.BaseEntity
import com.faltenreich.releaseradar.data.model.Entity
import com.faltenreich.releaseradar.tag
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlin.reflect.KClass

abstract class ParseDao<T : Entity>(private val clazz: KClass<T>) : Dao<T> {
    abstract val entityName: String

    protected fun getQuery(): ParseQuery<ParseObject> {
        return ParseQuery.getQuery<ParseObject>(entityName)
    }

    protected fun ParseQuery<ParseObject>.findInBackground(onResult: (List<T>) -> Unit) {
        findInBackground { parseObjects, exception ->
            if (exception == null) {
                val entities = parseObjects.mapNotNull { parseObject -> ParseObjectFactory.createEntity(clazz, parseObject) }
                onResult(entities)
            } else {
                Log.e(tag, exception.message)
                onResult(listOf())
            }
        }
    }

    override fun getById(id: String, onResult: (T?) -> Unit) {
        getQuery().whereEqualTo(BaseEntity.ID, id).findInBackground { entities -> onResult(entities.firstOrNull()) }
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<T>) -> Unit) {
        getQuery().whereContainedIn(BaseEntity.ID, ids).findInBackground(onResult)
    }
}