package com.faltenreich.releaseradar.firebase.database.dao

import com.faltenreich.releaseradar.firebase.database.model.FirebaseEntity

interface FirebaseDaoApi<MODEL : FirebaseEntity> {

    fun getAll(onSuccess: (List<MODEL>) -> Unit, onError: ((Exception) -> Unit)? = null)

    fun getById(id: String, onSuccess: (MODEL?) -> Unit, onError: ((Exception) -> Unit)? = null)

    fun createOrUpdate(entity: MODEL, onSuccess: ((Unit) -> Unit)? = null, onError: ((Exception) -> Unit)? = null)

    fun delete(entity: MODEL, onSuccess: ((Unit) -> Unit)? = null, onError: ((Exception) -> Unit)? = null)
}
