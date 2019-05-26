package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.GenreDao
import com.faltenreich.release.data.model.Genre

class GenreRepository(dao: GenreDao) : Repository<Genre, GenreDao>(dao)