package com.faltenreich.releaseradar.parse.database

import com.faltenreich.releaseradar.data.model.Storable
import com.parse.ParseObject

object ParseObjectFactory {

    fun createParseObject(entity: Storable): ParseObject? {
        TODO()
    }

    fun <Model : Storable> createStorable(parseObject: ParseObject): Model? {
        TODO()
    }
}