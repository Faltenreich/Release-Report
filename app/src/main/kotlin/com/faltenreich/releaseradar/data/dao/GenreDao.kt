package com.faltenreich.releaseradar.data.dao

import com.faltenreich.releaseradar.data.model.Genre

object GenreDao : BaseDao<Genre>(Genre::class) {
    override val entityName: String = "Genre"
}