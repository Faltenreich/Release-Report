package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.GenreDao
import com.faltenreich.releaseradar.data.model.Genre

class GenreRepository(dao: GenreDao) : Repository<Genre, GenreDao>(dao)