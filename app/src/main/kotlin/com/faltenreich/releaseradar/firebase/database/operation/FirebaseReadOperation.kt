package com.faltenreich.releaseradar.firebase.database.operation

import com.faltenreich.releaseradar.firebase.database.model.FirebaseEntity
import com.google.firebase.database.DataSnapshot
import kotlin.reflect.KClass

internal data class FirebaseReadOperation<MODEL : FirebaseEntity, RESPONSE : Any?>(
    val clazz: KClass<MODEL>,
    override val path: String,
    override val onSuccess: ((RESPONSE) -> Unit)?,
    override val onError: ((Exception) -> Unit)?,
    override val operation: (DataSnapshot) -> RESPONSE
) : FirebaseOperation<RESPONSE, DataSnapshot, RESPONSE, (DataSnapshot) -> RESPONSE>
