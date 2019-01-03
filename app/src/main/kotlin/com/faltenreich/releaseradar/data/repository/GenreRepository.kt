package com.faltenreich.releaseradar.data.repository

import com.faltenreich.releaseradar.data.dao.GenreDao
import com.faltenreich.releaseradar.data.model.Genre

object GenreRepository : Repository<Genre, GenreDao>(GenreDao)