package com.faltenreich.release.framework.parse.database

import android.text.format.DateUtils
import com.faltenreich.release.data.dao.Dao
import com.faltenreich.release.data.model.Model
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

interface ParseDao<T : Model> : Dao<T> {
    val clazz: KClass<T>
    val modelName: String

    fun getQuery(): ParseQuery<ParseObject> {
        return ParseQuery.getQuery(modelName)
    }

    suspend fun ParseQuery<ParseObject>.query(): List<T> {
        val query = this
        return withContext(Dispatchers.IO) {
            val parseObjects = query
                .setCachePolicy(CACHE_POLICY)
                .setMaxCacheAge(CACHE_AGE_MAX)
                .find()
            val entities = parseObjects?.mapNotNull { parseObject ->
                ParseObjectFactory.createEntity(clazz, parseObject)
            }
            return@withContext entities ?: listOf()
        }
    }

    override suspend fun getById(id: String): T? {
        return getQuery().whereEqualTo(Model.ID, id).query().firstOrNull()
    }

    override suspend fun getByIds(ids: Collection<String>): List<T> {
        return getQuery().whereContainedIn(Model.ID, ids).query()
    }

    companion object {
        private val CACHE_POLICY = ParseQuery.CachePolicy.CACHE_ELSE_NETWORK
        private const val CACHE_AGE_MAX = DateUtils.DAY_IN_MILLIS
    }
}