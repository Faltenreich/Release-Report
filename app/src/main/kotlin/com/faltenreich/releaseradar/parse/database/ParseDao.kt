package com.faltenreich.releaseradar.parse.database

import com.faltenreich.releaseradar.data.dao.Dao
import com.faltenreich.releaseradar.data.model.Storable
import com.faltenreich.releaseradar.firebase.database.FirebaseQuery
import com.parse.ParseObject
import com.parse.ParseQuery

abstract class ParseDao<Entity : Storable> : Dao<Entity> {

    override fun getAll(query: FirebaseQuery?, onSuccess: (List<Entity>) -> Unit, onError: ((Exception?) -> Unit)?) {
        onSuccess(listOf())
    }

    override fun getById(id: String, onSuccess: (Entity?) -> Unit, onError: ((Exception?) -> Unit)?) {
        ParseQuery.getQuery<ParseObject>("").getInBackground(id) { parseObject, exception ->
            ParseObjectFactory.createStorable<Entity>(parseObject)?.let { storable -> onSuccess(storable) } ?: onError?.invoke(exception)
        }
    }

    override fun createOrUpdate(entity: Entity, onSuccess: (() -> Unit)?, onError: ((Exception?) -> Unit)?) {
        ParseObjectFactory.createParseObject(entity)?.let { parseObject ->
            parseObject.saveInBackground { exception -> exception?.let { onError?.invoke(exception) } ?: onSuccess?.invoke() }
        } ?: onError?.invoke(null)
    }

    override fun delete(entity: Entity, onSuccess: (() -> Unit)?, onError: ((Exception?) -> Unit)?) {
        // TODO
    }
}