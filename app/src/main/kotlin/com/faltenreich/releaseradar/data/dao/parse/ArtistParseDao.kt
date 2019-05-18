package com.faltenreich.releaseradar.data.dao.parse

import com.faltenreich.releaseradar.data.dao.ArtistDao
import com.faltenreich.releaseradar.data.model.Artist
import com.faltenreich.releaseradar.parse.database.ParseDao
import kotlin.reflect.KClass

class ArtistParseDao : ArtistDao, ParseDao<Artist> {
    override val clazz: KClass<Artist> = Artist::class
    override val modelName: String = "Artist"
}