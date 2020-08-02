package com.faltenreich.release.data.repository

import com.faltenreich.release.data.dao.GenreDao
import com.faltenreich.release.data.dao.factory.DaoFactory
import com.faltenreich.release.data.model.Genre

object GenreRepository : Repository<Genre>, GenreDao by DaoFactory.dao(GenreDao::class)