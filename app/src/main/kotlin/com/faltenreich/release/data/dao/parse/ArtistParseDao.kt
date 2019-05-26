package com.faltenreich.release.data.dao.parse

import com.faltenreich.release.data.dao.ArtistDao
import com.faltenreich.release.data.model.Artist
import com.faltenreich.release.parse.database.ParseDao
import kotlin.reflect.KClass

class ArtistParseDao : ArtistDao, ParseDao<Artist> {
    override val clazz: KClass<Artist> = Artist::class
    override val modelName: String = "Artist"
}