package com.faltenreich.releaseradar.firebase.database.dao

import com.faltenreich.releaseradar.firebase.database.FirebaseRealtimeDatabase
import com.faltenreich.releaseradar.firebase.database.model.FirebaseEntity
import com.faltenreich.releaseradar.firebase.database.model.FirebaseNodeProvider
import com.faltenreich.releaseradar.firebase.database.operation.FirebaseReadOperation
import com.faltenreich.releaseradar.firebase.database.operation.FirebaseWriteOperation
import com.google.firebase.FirebaseException
import kotlin.reflect.KClass

abstract class FirebaseDao<MODEL : FirebaseEntity>(protected val clazz: KClass<MODEL>) : FirebaseApi<MODEL>, FirebaseNodeProvider<MODEL> {

    private val database = FirebaseRealtimeDatabase

    override fun getAll(onSuccess: (List<MODEL>) -> Unit, onError: ((Exception) -> Unit)?) {
        database.read(FirebaseReadOperation(clazz, buildPath(), onSuccess, onError) { data -> data.children.mapNotNull { child -> child.getValue(clazz.java)?.apply { id = child.key } } })
    }

    override fun getById(id: String, onSuccess: (MODEL?) -> Unit, onError: ((Exception) -> Unit)?) {
        database.read(FirebaseReadOperation(clazz, buildPath(id), onSuccess, onError) { data -> data.getValue(clazz.java)?.apply { this.id = data.key } })
    }

    override fun createOrUpdate(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?): Unit = when (entity.id) {
        null -> create(entity, onSuccess, onError)
        else -> update(entity, onSuccess, onError)
    }

    override fun create(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) {
        database.generateId(buildPath())?.let { id ->
            entity.id = id
            update(entity, onSuccess, onError)
        } ?: onError?.invoke(FirebaseException("Failed to generate id"))
    }

    override fun update(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) {
        database.write(FirebaseWriteOperation(buildPath(entity), onSuccess, onError) { databaseReference -> databaseReference.setValue(entity) })
    }

    override fun delete(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) {
        database.write(FirebaseWriteOperation(buildPath(entity), onSuccess, onError) { databaseReference -> databaseReference.removeValue() })
    }
}
