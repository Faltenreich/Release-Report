package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.data.dao.GenreDao
import com.faltenreich.release.data.model.Genre
import com.faltenreich.release.framework.parse.database.ParseDao
import kotlin.reflect.KClass

class GenreParseDao : GenreDao, ParseDao<Genre> {
    override val clazz: KClass<Genre> = Genre::class
    override val modelName: String = "Genre"
}