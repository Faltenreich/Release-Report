package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Model
import com.faltenreich.releaseradar.parse.database.ParseDao
import kotlin.reflect.KClass

abstract class BaseDao<T : Model>(override val clazz: KClass<T>) : ParseDao<T>