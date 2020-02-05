package com.faltenreich.release.data.dao.demo

import com.faltenreich.release.data.dao.GenreDao
import com.faltenreich.release.data.model.Genre

class GenreDemoDao : GenreDao {

    private val genres by lazy { DemoFactory.genres }

    override fun getById(id: String, onResult: (Genre?) -> Unit) {
        onResult(genres.firstOrNull { genre -> genre.id == id })
    }

    override fun getByIds(ids: Collection<String>, onResult: (List<Genre>) -> Unit) {
        onResult(genres.filter { genre -> ids.contains(genre.id) })
    }
}