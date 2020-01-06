package com.faltenreich.release.framework.parse.database

import android.text.format.DateUtils
import android.util.Log
import com.faltenreich.release.base.log.tag
import com.faltenreich.release.data.dao.Dao
import com.faltenreich.release.data.model.Model
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlin.reflect.KClass

interface ParseDao<T : Model> : Dao<T> {
    val clazz: KClass<T>
    val modelName: String

    fun getQuery(): ParseQuery<ParseObject> {
        return ParseQuery.getQuery(modelName)
    }

    fun ParseQuery<ParseObject>.findInBackground(onResult: (List<T>) -> Unit) = this
        .setCachePolicy(CACHE_POLICY)
        .setMaxCacheAge(CACHE_AGE_MAX)
        .findInBackground { parseObjects, exception ->
            if (exception == null) {
                val entities = parseObjects.mapNotNull { parseObject ->
                    ParseObjectFactory.createEntity(clazz, parseObject)
                }
                onResult(entities)
            } else {
                Log.e(tag, exception.message)
                onResult(listOf())
            }
        }

    override fun getById(id: String, onResult: (T?) -> Unit) {
        getQuery().whereEqualTo(Model.ID, id).findInBackground { entities ->
            onResult(entities.firstOrNull())
        }
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<T>) -> Unit) {
        getQuery().whereContainedIn(Model.ID, ids).findInBackground(onResult)
    }

    companion object {
        private val CACHE_POLICY = ParseQuery.CachePolicy.CACHE_ELSE_NETWORK
        private const val CACHE_AGE_MAX = DateUtils.DAY_IN_MILLIS
    }
}