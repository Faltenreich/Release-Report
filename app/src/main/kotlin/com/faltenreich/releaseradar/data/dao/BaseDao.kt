package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Entity
import com.faltenreich.releaseradar.parse.database.ParseDao
import kotlin.reflect.KClass

abstract class BaseDao<T : Entity>(override val clazz: KClass<T>) : ParseDao<T>