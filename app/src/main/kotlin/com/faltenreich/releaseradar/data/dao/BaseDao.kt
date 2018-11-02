package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.BaseEntity
import kotlin.reflect.KClass

abstract class BaseDao<MODEL : BaseEntity>(clazz: KClass<MODEL>) : FirebaseDao<MODEL>(clazz) {

    override val nodeName: String
        get() = clazz.java.simpleName.toLowerCase()

    override val nodeRootPath: String
        get() = when (this) {
            is UserDependency -> nodeRootPathForCurrentUser
            else -> super.nodeRootPath
        }
}
