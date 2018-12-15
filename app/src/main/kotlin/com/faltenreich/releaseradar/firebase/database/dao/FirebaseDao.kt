package com.faltenreich.releaseradar.firebase.database.dao

import com.faltenreich.releaseradar.firebase.database.FirebaseRealtimeDatabase
import com.faltenreich.releaseradar.firebase.database.model.FirebaseEntity
import com.faltenreich.releaseradar.firebase.database.query
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlin.reflect.KClass

abstract class FirebaseDao<MODEL : FirebaseEntity>(protected val clazz: KClass<MODEL>) : FirebaseDaoApi<MODEL>, FirebaseNodeProvider<MODEL> {

    private val database = FirebaseRealtimeDatabase

    override fun generateId(path: String): String? = database.createReference(path).push().key

    override fun getAll(query: FirebaseQuery?, onSuccess: (List<MODEL>) -> Unit, onError: ((Exception) -> Unit)?) {
        database.createReference(buildPath()).run { query?.let { query(it) } ?: this }.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                try {
                    val value = data.children.mapNotNull { child -> child.getValue(clazz.java)?.apply { id = child.key } }
                    println("Successfully read data: $value")
                    onSuccess(value)
                } catch (exception: Exception) {
                    println("Failed to parse data: ${exception.message}")
                    onError?.invoke(exception)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                println("Failed to read data: ${error.message}")
                onError?.invoke(error.toException())
            }
        })
    }

    override fun getById(id: String, onSuccess: (MODEL?) -> Unit, onError: ((Exception) -> Unit)?) {
        database.createReference(buildPath(id)).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                try {
                    val value = data.getValue(clazz.java)?.apply { this.id = data.key }
                    println("Successfully read data: $value")
                    onSuccess(value)
                } catch (exception: Exception) {
                    println("Failed to parse data: ${exception.message}")
                    onError?.invoke(exception)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                println("Failed to read data: ${error.message}")
                onError?.invoke(error.toException())
            }
        })
    }

    override fun createOrUpdate(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?): Unit = when (entity.id) {
        null -> create(entity, onSuccess, onError)
        else -> update(entity, onSuccess, onError)
    }

    private fun create(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) {
        generateId(buildPath())?.let { id ->
            entity.id = id
            update(entity, onSuccess, onError)
        } ?: onError?.invoke(FirebaseException("Failed to generate id"))
    }

    private fun update(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) {
        database.createReference(buildPath(entity)).setValue(entity) { error, _ ->
            when (error) {
                null -> onSuccess?.invoke(Unit)
                else -> onError?.invoke(error.toException())
            }
        }
    }

    override fun delete(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) {
        database.createReference(buildPath(entity)).removeValue { error, _ ->
            when (error) {
                null -> onSuccess?.invoke(Unit)
                else -> onError?.invoke(error.toException())
            }
        }
    }
}
