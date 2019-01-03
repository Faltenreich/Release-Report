package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Entity
import com.faltenreich.releaseradar.data.provider.UserDependency
import com.faltenreich.releaseradar.firebase.auth.FirebaseAuth
import com.faltenreich.releaseradar.firebase.database.dao.FirebaseDao
import kotlin.reflect.KClass

abstract class Dao<MODEL : Entity>(clazz: KClass<MODEL>) : FirebaseDao<MODEL>(clazz) {

    override val nodeRootPath: String
        get() = when (this) {
            is UserDependency -> nodeRootPathForCurrentUser
            else -> ""
        }

    override val nodeName: String
        get() = clazz.java.simpleName.toLowerCase()

    protected fun ensureUserAccount(action: (success: Boolean) -> Unit) = when (this !is UserDependency || FirebaseAuth.isSignedIn) {
        true -> action(true)
        false -> FirebaseAuth.login(onSuccess = {
            action(true)
        }, onError = {
            action(false)
        })
    }
}
