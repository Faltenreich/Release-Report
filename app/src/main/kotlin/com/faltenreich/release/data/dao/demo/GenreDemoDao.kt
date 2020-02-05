package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.dao.GenreDao
import com.faltenreich.release.data.model.Genre

class GenreDemoDao : GenreDao {

    private val genres by lazy { DemoFactory.genres }

    override suspend fun getById(id: String): Genre? {
        return genres.firstOrNull { genre -> genre.id == id }
    }

    override suspend fun getByIds(ids: Collection<String>): List<Genre> {
        return genres.filter { genre -> ids.contains(genre.id) }
    }
}