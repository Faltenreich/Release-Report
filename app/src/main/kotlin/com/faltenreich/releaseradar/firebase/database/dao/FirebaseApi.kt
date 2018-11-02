package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.FirebaseEntity

interface FirebaseApi<MODEL : FirebaseEntity> {

    fun getAll(onSuccess: (List<MODEL>) -> Unit, onError: ((Exception) -> Unit)? = null)

    fun getById(id: String, onSuccess: (MODEL?) -> Unit, onError: ((Exception) -> Unit)? = null)

    fun createOrUpdate(entity: MODEL, onSuccess: ((Unit) -> Unit)? = null, onError: ((Exception) -> Unit)? = null)

    fun create(entity: MODEL, onSuccess: ((Unit) -> Unit)? = null, onError: ((Exception) -> Unit)? = null)

    fun update(entity: MODEL, onSuccess: ((Unit) -> Unit)? = null, onError: ((Exception) -> Unit)? = null)

    fun delete(entity: MODEL, onSuccess: ((Unit) -> Unit)? = null, onError: ((Exception) -> Unit)? = null)
}
