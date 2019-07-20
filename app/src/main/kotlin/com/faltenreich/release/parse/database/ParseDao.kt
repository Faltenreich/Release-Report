package com.faltenreich.release.parse.database

import android.util.Log
import com.faltenreich.release.data.dao.Dao
import com.faltenreich.release.data.model.Model
import com.faltenreich.release.extension.tag
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlin.reflect.KClass

interface ParseDao<T : Model> : Dao<T> {
    val clazz: KClass<T>
    val modelName: String

    fun getQuery(): ParseQuery<ParseObject> {
        return ParseQuery.getQuery(modelName)
    }

    fun ParseQuery<ParseObject>.findInBackground(onResult: (List<T>) -> Unit) {
        findInBackground { parseObjects, exception ->
            if (exception == null) {
                onResult(parseObjects.mapNotNull { parseObject -> ParseObjectFactory.createEntity(clazz, parseObject) })
            } else {
                Log.e(tag, exception.message)
                onResult(listOf())
            }
        }
    }

    override fun getById(id: String, onResult: (T?) -> Unit) {
        getQuery().whereEqualTo(Model.ID, id).findInBackground { entities -> onResult(entities.firstOrNull()) }
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<T>) -> Unit) {
        getQuery().whereContainedIn(Model.ID, ids).findInBackground(onResult)
    }
}