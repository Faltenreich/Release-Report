package com.faltenreich.release.parse.database

import com.faltenreich.release.data.model.Model
import com.parse.ParseObject
import kotlin.reflect.KClass

object ParseObjectFactory {
    fun <T : Model> createEntity(clazz: KClass<T>, parseObject: ParseObject): T? {
        val entity = clazz.java.newInstance()
        entity.fromParseObject(parseObject)
        return entity
    }
}