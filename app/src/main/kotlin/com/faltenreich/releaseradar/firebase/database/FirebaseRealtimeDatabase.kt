package com.faltenreich.releaseradar.firebase.database

import com.faltenreich.releaseradar.firebase.database.model.FirebaseEntity
import com.faltenreich.releaseradar.firebase.database.operation.FirebaseReadOperation
import com.faltenreich.releaseradar.firebase.database.operation.FirebaseWriteOperation
import com.faltenreich.releaseradar.logging.LogLevel
import com.faltenreich.releaseradar.logging.log
import com.google.firebase.database.*

internal object FirebaseRealtimeDatabase {

    private val database: FirebaseDatabase
        get() = FirebaseDatabase.getInstance()

    private fun createReference(path: String): DatabaseReference = database.getReference(path)

    fun generateId(path: String): String? = createReference(path).push().key

    fun <MODEL : FirebaseEntity, RESPONSE : Any?> read(request: FirebaseReadOperation<MODEL, RESPONSE>) = createReference(request.path).addValueEventListener(object :
        ValueEventListener {
        override fun onDataChange(data: DataSnapshot) {
            try {
                val value = request.operation(data)
                log("Successfully read data: $value")
                request.onSuccess?.invoke(value)
            } catch (exception: Exception) {
                log("Failed to parse data: ${exception.message}", LogLevel.ERROR)
                request.onError?.invoke(exception)
            }
        }
        override fun onCancelled(error: DatabaseError) {
            log("Failed to read data: ${error.message}", LogLevel.ERROR)
            request.onError?.invoke(error.toException())
        }
    })

    fun write(request: FirebaseWriteOperation) = createReference(request.path).run {
        request.operation(this).addOnSuccessListener {
            log("Successfully posted data")
            request.onSuccess?.invoke(Unit)
        }.addOnFailureListener { exception ->
            log("Failed to write data: $exception", LogLevel.ERROR)
            request.onError?.invoke(exception)
        }
    }
}
