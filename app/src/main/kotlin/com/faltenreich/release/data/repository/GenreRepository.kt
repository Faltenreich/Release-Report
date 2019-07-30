package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.GenreDao
import com.faltenreich.release.data.model.Genre

object GenreRepository : Repository<Genre, GenreDao>(GenreDao::class)