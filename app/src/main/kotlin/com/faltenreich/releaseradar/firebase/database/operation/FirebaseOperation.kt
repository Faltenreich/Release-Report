package com.faltenreich.releaseradar.firebase.database.operation

internal interface FirebaseOperation <
        RESPONSE : Any?,
        CRUD_CONTEXT : Any,
        CRUD_RESPONSE : Any?,
        CRUD_OPERATION : (CRUD_CONTEXT) -> CRUD_RESPONSE
        > {
    val path: String
    val onSuccess: ((RESPONSE) -> Unit)?
    val onError: ((Exception) -> Unit)?
    val operation: CRUD_OPERATION
}
