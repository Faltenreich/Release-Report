package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Entity
import com.faltenreich.releaseradar.data.provider.UserDependency
import kotlin.reflect.KClass

abstract class Dao<MODEL : Entity>(clazz: KClass<MODEL>) : FirebaseDao<MODEL>(clazz) {

    override val nodeName: String
        get() = clazz.java.simpleName.toLowerCase()

    override val nodeRootPath: String
        get() = when (this) {
            is UserDependency -> nodeRootPathForCurrentUser
            else -> super.nodeRootPath
        }
}
