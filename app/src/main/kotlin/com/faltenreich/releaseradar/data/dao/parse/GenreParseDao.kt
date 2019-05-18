package com.faltenreich.releaseradar.data.dao.parse

import com.faltenreich.releaseradar.data.dao.GenreDao
import com.faltenreich.releaseradar.data.model.Genre
import com.faltenreich.releaseradar.parse.database.ParseDao
import kotlin.reflect.KClass

class GenreParseDao : GenreDao, ParseDao<Genre> {
    override val clazz: KClass<Genre> = Genre::class
    override val modelName: String = "Genre"
}