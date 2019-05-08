package com.faltenreich.releaseradar.parse.database

import com.faltenreich.releaseradar.data.model.Entity
import com.parse.ParseObject
import kotlin.reflect.KClass

object ParseObjectFactory {

    fun <T : Entity> createEntity(clazz: KClass<T>, parseObject: ParseObject): T? {
        val entity = clazz.java.newInstance()
        entity.fromParseObject(parseObject)
        return entity
    }
}