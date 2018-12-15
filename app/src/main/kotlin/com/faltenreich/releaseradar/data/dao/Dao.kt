package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Entity
import com.faltenreich.releaseradar.data.provider.UserDependency
import com.faltenreich.releaseradar.firebase.auth.FirebaseAuth
import com.faltenreich.releaseradar.firebase.database.dao.FirebaseDao
import com.faltenreich.releaseradar.firebase.database.model.FirebaseQuery
import javax.security.auth.login.LoginException
import kotlin.reflect.KClass

abstract class Dao<MODEL : Entity>(clazz: KClass<MODEL>) : FirebaseDao<MODEL>(clazz) {

    override val nodeRootPath: String
        get() = when (this) {
            is UserDependency -> nodeRootPathForCurrentUser
            else -> ""
        }

    override val nodeName: String
        get() = clazz.java.simpleName.toLowerCase()

    private fun ensureUserAccount(action: (success: Boolean) -> Unit) = when (this !is UserDependency || FirebaseAuth.isSignedIn) {
        true -> action(true)
        false -> FirebaseAuth.login(onSuccess = {
            action(true)
        }, onError = {
            action(false)
        })
    }

    override fun getAll(query: FirebaseQuery?, onSuccess: (List<MODEL>) -> Unit, onError: ((Exception) -> Unit)?) {
        ensureUserAccount { success ->
            when (success) {
                true -> super.getAll(query, onSuccess, onError)
                false -> onError?.invoke(LoginException())
            }
        }
    }

    override fun getById(id: String, onSuccess: (MODEL?) -> Unit, onError: ((Exception) -> Unit)?) {
        ensureUserAccount { success ->
            when (success) {
                true -> super.getById(id, onSuccess, onError)
                false -> onError?.invoke(LoginException())
            }
        }
    }

    override fun createOrUpdate(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) {
        ensureUserAccount { success ->
            when (success) {
                true -> super.createOrUpdate(entity, onSuccess, onError)
                false -> onError?.invoke(LoginException())
            }
        }
    }

    override fun delete(entity: MODEL, onSuccess: ((Unit) -> Unit)?, onError: ((Exception) -> Unit)?) {
        ensureUserAccount { success ->
            when (success) {
                true -> super.delete(entity, onSuccess, onError)
                false -> onError?.invoke(LoginException())
            }
        }
    }
}
