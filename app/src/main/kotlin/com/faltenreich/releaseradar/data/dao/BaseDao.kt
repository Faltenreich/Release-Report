package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Storable
import com.faltenreich.releaseradar.parse.database.ParseDao
import kotlin.reflect.KClass

abstract class BaseDao<MODEL : Storable>(clazz: KClass<MODEL>) : ParseDao<MODEL>()