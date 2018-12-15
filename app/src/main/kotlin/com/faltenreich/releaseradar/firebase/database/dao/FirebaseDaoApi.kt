package com.faltenreich.releaseradar.firebase.database.dao

import com.faltenreich.releaseradar.firebase.database.model.FirebaseEntity

interface FirebaseDaoApi<MODEL : FirebaseEntity> {

    fun generateId(path: String): String?

    fun getAll(filter: Pair<String, String>? = null, orderBy: String? = null, onSuccess: (List<MODEL>) -> Unit, onError: ((Exception) -> Unit)? = null)

    fun getById(id: String, onSuccess: (MODEL?) -> Unit, onError: ((Exception) -> Unit)? = null)

    fun createOrUpdate(entity: MODEL, onSuccess: ((Unit) -> Unit)? = null, onError: ((Exception) -> Unit)? = null)

    fun delete(entity: MODEL, onSuccess: ((Unit) -> Unit)? = null, onError: ((Exception) -> Unit)? = null)
}
